"C:\Program Files\MySQL\MySQL Server 5.7\bin\mysqldump" -t -u lineup -plineup lineup --tables sec_constraint sec_filter sec_group  sec_permission sec_role sec_session_attr sec_user sec_user_role --where="created_by is not NULL and delete_ts is null" --result-file="C:\Users\Christian\Documents\GitHub\lineup\modules\core\db\init\mysql\40.create-db.sql"  --skip-triggers

"C:\Program Files\MySQL\MySQL Server 5.7\bin\mysqldump" -t -u lineup -plineup lineup --tables sec_group_hierarchy sec_user_setting --where="created_by is not NULL" --result-file="C:\Users\Christian\Documents\GitHub\lineup\modules\core\db\init\mysql\45.create-db.sql"  --skip-triggers

"C:\Program Files\MySQL\MySQL Server 5.7\bin\mysqldump" -t -u lineup -plineup lineup --tables LINEUP_MODE_OF_TRANSFER LINEUP_CRAFT_TYPE LINEUP_SITE_CATEGORY LINEUP_SITE  --where="created_by is not NULL and delete_ts is null" --result-file="C:\Users\Christian\Documents\GitHub\lineup\modules\core\db\init\mysql\50.create-db.sql"  --skip-triggers