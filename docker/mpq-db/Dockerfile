FROM postgres:10.3

ENV MPQ_DB_INIT_DIR=/tmp/mpq-db \
    MPQ_DB_USER=mpqdata

RUN mkdir -p /docker-entrypoint-initdb.d/ && \
    mkdir -p $MPQ_DB_INIT_DIR/sql/table &&\
    mkdir -p $MPQ_DB_INIT_DIR/sql/schema &&\
    mkdir -p $MPQ_DB_INIT_DIR/data/

COPY init-mpq-db.sh /docker-entrypoint-initdb.d/ 

COPY sql/schema/*.sql $MPQ_DB_INIT_DIR/sql/schema/
COPY sql/table/*.sql $MPQ_DB_INIT_DIR/sql/table/
COPY data/*.csv $MPQ_DB_INIT_DIR/data/

