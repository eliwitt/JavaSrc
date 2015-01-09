use facets_test_01
go
/****************************************************************************
$Header:$

Description   -     Stored procedure (CPSSP_VRU_MAIN) is executed by the
                    VRU process to obtain the necessary information to
                    be announced to a subscriber calling to check the 
                    status of their account.

Short Name    -     CPSVRUMAIN.SQP

Procedures    -     none

Tables        -     CMC_GRGR_GROUP
                    CMC_SBSB_SUBSC
                    CMC_MEME_MEMBER
                    CMC_MEPE_PRCS_ELIG
                    CMC_BLEI_ENTY_INFO
                    CMC_BLBL_BILL_SUMM
                    CMC_RCPT_RECEIPTS

Inception     -     Creation Date:  09/24/2003
                    Creator:  D. RIEVLEY
                    Reason:  This procedure was created to be the main
                             driver procedure for retrieving the required
                             information for the VRU.  It will query 
                             various tables and print a comma delimited
                             string of data for its output.  Should a
                             value not be found for RCPT, BLBL, or MEPE,
                             the queries should continue on in order to
                             obtain all other data that exists.  Only
                             critical errors (RC= 21-23) should stop all
                             other queries from running.
                                  
                    Notes:  (1) The query for receipts was broken into three
                            seperate queries in order to match the indexes
                            on the RCPT table and to obtain faster results.
                                  
                    Return Codes:  21 - indicates the SBSB_ID was not passed
                                        to the procedure.
                                   22 - indicates that the SBSB_ID passed to
                                        the procedure does not exist in 
                                        Facets.
                                   23 - indicates that a valid billing entity
                                        was not found in Facets for the passed
                                        SBSB_ID.
                                   24 - used within the procedure to keep from
                                        executing additional RCPT queries if a 
                                        row is not found.  A RC=24 is converted
                                        back to a 0 after the last RCPT query.

Modifications -     Modified Date:  12/05/2005
                    Modifier:  J. C. Howell
                    Reason:  Meetings have been held recently to discuss actual
                             implementation of the VRU for Individual Billing
                             and Customer Service.  From these meetings, it was
                             decided that the input to the stored procedure
                             would only be the subscriber id and not both
                             subscriber id and group id.  Modified the logic to
                             accept only subscriber id as input and perform a 
                             query to determine the group id.  It was also
                             decided to include the subscriber's last name as
                             one of the information items to be obtained and
                             announced to the subscriber.  Modified the logic
                             to query for the subscriber's last name.

                   Modified Date:  04/02/2008
                   Modifier:  J. C. Howell
                   Reason:  This stored procedure is being used by a web
                            application that allows members to pay their
                            bill online by credit card.  This new type of
                            transaction needs this stored procedure to pull
                            the Billing Due Date.  Modified the logic to pull
                            the Billing Due Date from BLBL_BILL_SUMM.

$Log:$

****************************************************************************/

/****************************************************************************
**  Drop the CPSSP_VRU_MAIN Procedure                                      **
****************************************************************************/

if exists
   (select *
    from sysobjects
    where type = "P"
    and name = "CPSSP_VRU_MAIN")

BEGIN
   drop procedure CPSSP_VRU_MAIN
END

go

/****************************************************************************
**  Create the CPSSP_AUTO_NUM_MAIN Procedure                               **
****************************************************************************/

create procedure CPSSP_VRU_MAIN

/****************************************************************************
**  Declare Argument(s)                                                    **
****************************************************************************/

@pSBSB_ID char(009) = NULL

/****************************************************************************
**  Begin Procedure Code                                                   **
****************************************************************************/

as

/****************************************************************************
**  Declare Local Variables                                                **
****************************************************************************/

DECLARE @lnRetCd int
DECLARE @lnBLEI_CK int
DECLARE @lxGRGR_ID char(008)
DECLARE @lxSBSB_LAST_NAME char(035)
DECLARE @lxMEPE_ELIG_IND char(001)
DECLARE @lxRCPT_ID char(012)
DECLARE @lxRCPT_CREATE_DTM datetime
DECLARE @lxRCPT_RCVD_DT char(010)
DECLARE @lnRCPT_AMT money
DECLARE @lxBLBL_END_DT char(010)
DECLARE @lnBLEI_NET_DUE money
DECLARE @lnRowCount int
DECLARE @lxBLBL_DUE_DT char(010)

SELECT  @lnRetCd = 0

   /*************************************************************************
   **  Check to determine that SBSB_ID was passed to procedure             **
   *************************************************************************/
IF @lnRetCd = 0
BEGIN
   IF (@pSBSB_ID = NULL
       AND @lnRetCd = 0)
      BEGIN
         SELECT @lnRetCd = 21
      END
END

   /*************************************************************************
   **  Query to determine if a valid Subscriber exists and get the         **
   **  subscriber's last name                                              **
   *************************************************************************/
IF @lnRetCd = 0
BEGIN
   SELECT 
           @lxSBSB_LAST_NAME = SBSB_LAST_NAME
   FROM 
           dbo.CMC_SBSB_SUBSC
   WHERE
           SBSB_ID = @pSBSB_ID
        
   IF (@lxSBSB_LAST_NAME = NULL)
      BEGIN
         SELECT @lnRetCd = 22
      END
END

   /*************************************************************************
   **  Query to get the Group associated with the SBSB_ID passed to this   **
   **  procedure                                                           **
   *************************************************************************/
IF @lnRetCd = 0
BEGIN
   SELECT
           @lxGRGR_ID = A.GRGR_ID
   FROM
           dbo.CMC_GRGR_GROUP A,
           dbo.CMC_SBSB_SUBSC B
   WHERE
           A.GRGR_CK = B.GRGR_CK
   AND     B.SBSB_ID = @pSBSB_ID
END
                 
   /*************************************************************************
   **  Query to determine if a Billing Entity exists and get the total     **
   **  amount due on the account                                           **
   *************************************************************************/
IF @lnRetCd = 0
BEGIN
   SELECT 
           @lnBLEI_CK = C.BLEI_CK,
           @lnBLEI_NET_DUE = C.BLEI_NET_DUE
   FROM 
           dbo.CMC_GRGR_GROUP A,
           dbo.CMC_SBSB_SUBSC B,
           dbo.CMC_BLEI_ENTY_INFO C
   WHERE
           A.GRGR_CK = B.GRGR_CK
   AND     B.SBSB_CK = C.BLEI_BILL_LEVEL_CK
   AND     C.BLEI_BILL_LEVEL = 'I'
   AND     B.SBSB_ID = @pSBSB_ID
        
   IF (@lnBLEI_CK = NULL)
      BEGIN
         SELECT @lnRetCd = 23
      END
END

   /*************************************************************************
   **  Query to determine if a subscriber is eligible or not               **
   *************************************************************************/
IF @lnRetCd = 0
BEGIN
   SELECT 
           @lnRowCount = count(*)
   FROM 
           dbo.CMC_GRGR_GROUP A,
           dbo.CMC_SBSB_SUBSC B,
           dbo.CMC_MEME_MEMBER C,
           dbo.CMC_MEPE_PRCS_ELIG D
   WHERE
           A.GRGR_CK = B.GRGR_CK
   AND     B.GRGR_CK = C.GRGR_CK
   AND     B.SBSB_CK = C.SBSB_CK
   AND     C.MEME_CK = D.MEME_CK
   AND     C.GRGR_CK = D.GRGR_CK
   AND     C.MEME_REL = 'M'
   AND     D.MEPE_ELIG_IND = 'Y'
   AND     D.MEPE_EFF_DT <= getdate()
   AND     D.MEPE_TERM_DT > getdate()
   AND     B.SBSB_ID = @pSBSB_ID
        
      IF (@lnRowCount > 0)
         BEGIN
            SELECT @lxMEPE_ELIG_IND = 'Y'
         END
      ELSE
         BEGIN
            SELECT @lxMEPE_ELIG_IND = 'N'
         END
END

   /*************************************************************************
   **  Query to get the RCPT_CREATE_DTM for the last paid receipt          **
   *************************************************************************/
IF @lnRetCd = 0
BEGIN
   SELECT 
           @lxRCPT_CREATE_DTM = A.RCPT_CREATE_DTM
   FROM 
           dbo.CMC_RCPT_RECEIPTS A
   WHERE
           BLEI_CK = @lnBLEI_CK
   AND     RCPT_CREATE_DTM =
              (SELECT MAX(B.RCPT_CREATE_DTM)
               FROM   dbo.CMC_RCPT_RECEIPTS B
               WHERE  B.BLEI_CK = @lnBLEI_CK
               AND    B.RCPT_RCPT_CD in ('P','B')
               AND    B.RCPT_RVRS_IND = 'N')
                               
      IF (@lxRCPT_CREATE_DTM = NULL)
         BEGIN
            SELECT @lnRetCd = 24
         END
END

   /*************************************************************************
   **  Use the RCPT_CREATE_DTM to get the key to table                     **
   *************************************************************************/
IF @lnRetCd = 0
BEGIN
   SELECT 
           @lxRCPT_ID = RCPT_ID
   FROM 
           dbo.CMC_RCPT_RECEIPTS
   WHERE
           BLEI_CK = @lnBLEI_CK
   AND     RCPT_CREATE_DTM = @lxRCPT_CREATE_DTM

   IF (@lxRCPT_CREATE_DTM = NULL)
      BEGIN
         SELECT @lnRetCd = 24
      END
END

   /*************************************************************************
   **  Query to determine last payment date and amount                     **
   *************************************************************************/
IF @lnRetCd = 0
BEGIN
   SELECT 
           @lxRCPT_RCVD_DT = convert(char(10),RCPT_RCVD_DT,101),
           @lnRCPT_AMT = RCPT_AMT
   FROM 
           dbo.CMC_RCPT_RECEIPTS
   WHERE
           RCPT_ID = @lxRCPT_ID
END

   /*************************************************************************
   **  If no RCPT info is found change RC=24 back to RC=0                  **
   *************************************************************************/
IF @lnRetCd = 24
BEGIN
   SELECT @lnRetCd = 0
END

   /*************************************************************************
   **  Query to determine due date and last paid through date              **
   *************************************************************************/
IF (@lnRetCd = 0)
BEGIN
   SELECT
           @lxBLBL_DUE_DT = convert(char(10),A.BLBL_DUE_DT,101),
           @lxBLBL_END_DT = convert(char(10),A.BLBL_END_DT,101)
   FROM
           dbo.CMC_BLBL_BILL_SUMM A
   WHERE
           A.BLEI_CK = @lnBLEI_CK
   AND     A.BLBL_DUE_DT =
              (SELECT MAX(BLBL_DUE_DT)
               FROM   dbo.CMC_BLBL_BILL_SUMM B
               WHERE  B.BLBL_PAID_STS in ('2','3')
               AND    B.BLBL_SPCL_BL_IND in ('N','F')
               AND    B.BLEI_CK = @lnBLEI_CK)
END

   /*************************************************************************
   **  Print the results of the procedure                                  **
   *************************************************************************/

print "%1!,%2!,%3!,%4!,%5!,%6!,%7!,%8!,%9!,%10!", @lnRetCd, @lxGRGR_ID, @pSBSB_ID, @lxSBSB_LAST_NAME, @lxMEPE_ELIG_IND, @lxRCPT_RCVD
_DT, @lnRCPT_AMT, @lxBLBL_END_DT, @lnBLEI_NET_DUE, @lxBLBL_DUE_DT

select Return_Code=@lnRetCd, Group_Number=@lxGRGR_ID, Subscriber_Id=@pSBSB_ID, Last_Name=@lxSBSB_LAST_NAME, Eligibility_Ind=@lxMEPE_
ELIG_IND, Last_Pymt_Date=@lxRCPT_RCVD_DT, Last_Pymt_Amt=@lnRCPT_AMT, Last_Paid_Thru_Date=@lxBLBL_END_DT, Balance_Due=@lnBLEI_NET_DUE
, Billing_Due_Date=@lxBLBL_DUE_DT

RETURN @lnRetCd
go

grant execute on dbo.CPSSP_VRU_MAIN to vru_facets
go


