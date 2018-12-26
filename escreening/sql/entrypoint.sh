#!/bin/bash
database=SDC-PROD
wait_time=15s
password=Password12345678

# wait for SQL Server to come up
echo importing data will start in $wait_time...
sleep $wait_time
echo importing data...

# run the init script to create the DB and the tables in /table
/opt/mssql-tools/bin/sqlcmd -S 0.0.0.0 -U sa -P $password -i restore_sql_from_bak.sql
#/opt/mssql-tools/bin/sqlcmd -S 0.0.0.0 -U sa -P $password -i ./S1_CreateDB.sql
#/opt/mssql-tools/bin/sqlcmd -S 0.0.0.0 -U sa -P $password -i ./S0_PopRefAndTestData.sql
#/opt/mssql-tools/bin/sqlcmd -S 0.0.0.0 -U sa -P $password -i ./S2_UniqueNDXWithNull.sql
#/opt/mssql-tools/bin/sqlcmd -S 0.0.0.0 -U sa -P $password -i ./S4_MeasureParentFK.sql

echo "import data is done!"