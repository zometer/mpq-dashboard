#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE USER $MPQ_DB_USER;
    CREATE DATABASE mpq_data;
    ALTER DATABASE mpq_data SET search_path TO mpq_data;
    GRANT ALL PRIVILEGES ON DATABASE mpq_data TO $MPQ_DB_USER;
EOSQL

env

# create db objects
FILE="$MPQ_DB_INIT_DIR/sql/schema/MPQ_DATA.sql" && echo "processing $FILE" && psql -v ON_ERROR_STOP=1 -d mpq_data --username "$MPQ_DB_USER" -f $FILE
FILE="$MPQ_DB_INIT_DIR/sql/table/COLOR.sql" && echo "processing $FILE" && psql -v ON_ERROR_STOP=1 -d mpq_data --username "$MPQ_DB_USER" -f $FILE
FILE="$MPQ_DB_INIT_DIR/sql/table/MPQ_CHARACTER.sql" && echo "processing $FILE" && psql -v ON_ERROR_STOP=1 -d mpq_data --username "$MPQ_DB_USER" -f $FILE
FILE="$MPQ_DB_INIT_DIR/sql/table/CHARACTER_POWER.sql" && echo "processing $FILE" && psql -v ON_ERROR_STOP=1 -d mpq_data --username "$MPQ_DB_USER" -f $FILE

echo "setting default schema" && psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -c "ALTER DATABASE mpq_data SET search_path TO mpq_data;"

# load init data
FILE="$MPQ_DB_INIT_DIR/sql/load/load-color.sql" && echo "processing $FILE" && psql -v ON_ERROR_STOP=1 -d mpq_data --username "$POSTGRES_USER" -f $FILE
