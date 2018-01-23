package com.syzible.tearma.Deprecated.Persistence;

import android.provider.BaseColumns;

/**
 * Created by ed on 29/11/2016
 */

public abstract class Database {
    public static final String DB_NAME = "definitions_db";
    public static final int DB_VERSION = 1;

    public static abstract class Definitions implements BaseColumns {
        public static final String TABLE_NAME = "definitions_table";
        public static final String ID = "id";
        public static final String SEARCH_TERM = "searchTerm";
        public static final String SEARCH_TYPE = "searchType";
        public static final String SEARCH_MUTATIONS_ID = "search_mutations_id";
        public static final String LANGUAGE = "language";
        public static final String SIGNPOST = "signpost";
        public static final String MUTATIONS_ID = "mutations_id";
        public static final String DOMAINS_ID = "domains_id";
    }

    public static abstract class NounMutations implements BaseColumns {
        public static final String TABLE_NAME = "noun_mutations_table";
        public static final String ID = "id";
        public static final String DECLENSION = "declension";
        public static final String GENDER = "gender";
        public static final String ROOT = "root";
        public static final String GEN_SING = "gen_sing";
        public static final String NOM_PLU = "nom_plu";
        public static final String GEN_PLU = "gen_plu";
    }

    public static abstract class VerbMutations implements BaseColumns {
        public static final String TABLE_NAME = "verb_mutations_table";
        public static final String ID = "id";
        public static final String ROOT = "root";
        public static final String GERUND = "gen_sing";
        public static final String PARTICIPLE = "nom_plu";
    }

    public static abstract class Domains implements BaseColumns {
        public static final String TABLE_NAME = "domains_table";
        public static final String ID = "id";
        public static final String EN_DOMAIN = "en_domain";
        public static final String GA_DOMAIN = "ga_domain";
    }

    public static abstract class NounSearchMutations implements BaseColumns {
        public static final String TABLE_NAME = "noun_search_mutations_table";
        public static final String ID = "id";
        public static final String DECLENSION = "declension";
        public static final String GENDER = "gender";
        public static final String ROOT = "root";
        public static final String GEN_SING = "gen_sing";
        public static final String NOM_PLU = "nom_plu";
        public static final String GEN_PLU = "gen_plu";
    }

    public static abstract class VerbSearchMutations implements BaseColumns {
        public static final String TABLE_NAME = "verb_search_mutations_table";
        public static final String ID = "id";
        public static final String ROOT = "root";
        public static final String GERUND = "gen_sing";
        public static final String PARTICIPLE = "nom_plu";
    }
}
