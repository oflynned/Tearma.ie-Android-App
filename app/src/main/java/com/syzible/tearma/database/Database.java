package com.syzible.tearma.database;

import android.provider.BaseColumns;

/**
 * Created by ed on 29/11/2016
 */

abstract class Database {
    static final String DB_NAME = "definitions_db";
    static final int DB_VERSION = 1;

    static abstract class Definitions implements BaseColumns {
        static final String TABLE_NAME = "definitions_table";
        static final String ID = "id";
        static final String TERM = "term";
        static final String TYPE = "type";
        static final String SEARCH_MUTATIONS = "search_mutations";
        static final String LANGUAGE = "language";
        static final String SIGNPOST = "signpost";
        static final String MUTATIONS = "mutations";
        static final String DOMAINS = "domains";
    }

    static abstract class NounMutations implements BaseColumns {
        static final String TABLE_NAME = "mutations_table";
        static final String ID = "id";
        static final String DECLENSION = "declension";
        static final String GENDER = "gender";
        static final String ROOT = "root";
        static final String GEN_SING = "gen_sing";
        static final String NOM_PLU = "nom_plu";
        static final String GEN_PLU = "gen_plu";
    }

    static abstract class VerbMutations implements BaseColumns {
        static final String TABLE_NAME = "mutations_table";
        static final String ID = "id";
        static final String ROOT = "root";
        static final String GERUND = "gen_sing";
        static final String PARTICIPLE = "nom_plu";
    }

    static abstract class Domains implements BaseColumns {
        static final String TABLE_NAME = "domains_table";
        static final String ID = "id";
        static final String LANG = "lang";
        static final String DOMAIN = "domain";
    }
}
