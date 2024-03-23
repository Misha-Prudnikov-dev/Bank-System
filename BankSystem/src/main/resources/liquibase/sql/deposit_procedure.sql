CREATE DEFINER=`root`@`localhost` PROCEDURE `deposit`(IN amount decimal(10,2),IN accountId int,OUT transactionId int)
BEGIN

DECLARE carrency_id INT;
DECLARE transaction_type_id INT;

SELECT currencies_id_currencies into carrency_id FROM accounts acc WHERE acc.id_accounts=accountId;
SELECT id_type_transactions into transaction_type_id FROM type_transactions ty_tr WHERE ty_tr.name_type_transactions='ПОПОЛНЕНИЕ';

   START TRANSACTION;

   update accounts acc set acc.balance_accounts = acc.balance_accounts + amount where acc.id_accounts = accountId;

  insert into transactions
  (amount_transactions,date_transactions,accounts_id_accounts,type_transactions_id_type_transactions,currencies_id_currencies)
  values(amount,now(),accountId,transaction_type_id,carrency_id);

  SET transactionId = LAST_INSERT_ID();

  commit;

END