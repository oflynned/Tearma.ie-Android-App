package com.syzible.tearma.Common.Filters;

public enum Filters {
    new_results {
        @Override
        public String toString() {
            return "com.syzible.tearma.new_results";
        }
    },
    no_results {
        @Override
        public String toString() {
            return "com.syzible.tearma.no_results";
        }
    }
}
