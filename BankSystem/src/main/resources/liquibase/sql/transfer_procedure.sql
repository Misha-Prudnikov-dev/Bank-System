CREATE DEFINER=`root`@`localhost` PROCEDURE `transfer`(
IN amount decimal(10,2),
IN fromAccountId int,
IN toAccountId int,
IN exchange_rate decimal(10,2),
OUT transactionId int )
BEGIN

DECLARE balance decimal;
DECLARE carrency_id INT;
DECLARE transaction_type_id INT;

SELECT currencies_id_currencies,balance_accounts into carrency_id,balance FROM accounts acc WHERE acc.id_accounts=fromAccountId;
SELECT id_type_transactions into transaction_type_id FROM type_transactions ty_tr WHERE ty_tr.name_type_transactions='ПЕРЕВОД';

IF amount <= balance
THEN
   update accounts acc set acc.balance_accounts = acc.balance_accounts - amount where acc.id_accounts = fromAccountId;
   update accounts acc set acc.balance_accounts = acc.balance_accounts + amount where acc.id_accounts = toAccountId;

  insert into transactions
  (amount_transactions,date_transactions,accounts_id_accounts,to_id_accounts,type_transactions_id_type_transactions,currencies_id_currencies)
  values(amount,now(),fromAccountId,toAccountId,transaction_type_id,carrency_id);

  SET transactionId = LAST_INSERT_ID();

end if ;

END