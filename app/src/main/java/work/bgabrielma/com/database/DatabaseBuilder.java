package work.bgabrielma.com.database;

public class DatabaseBuilder {
    private StringBuffer _sql = new StringBuffer();

    public DatabaseBuilder select(String... fields) {
        this._sql.append("SELECT").append(" ");

        for(String field : fields) {
            this._sql.append(field);

            if(fields[fields.length - 1] != field)
                this._sql.append(", ");
        }

        // Add new line to formation
        this._sql.append("\n");
        return this;
    }

    public DatabaseBuilder from(String... fields) {
        this._sql.append("FROM").append(" ");

        for(String field : fields) {
            this._sql.append(field);

            if(fields[fields.length - 1] != field)
                this._sql.append(", ");
        }

        // Add new line to formation
        this._sql.append("\n");
        return this;
    }

    public DatabaseBuilder where() {
        this._sql.append("WHERE");
        return this;
    }

    public DatabaseBuilder and(String... conditions) {
        for(String condition : conditions) {
            this._sql.append(" " + condition);

            if(conditions[conditions.length - 1] != condition)
                this._sql.append(" AND");
        }

        this._sql.append("\n");
        return this;
    }

    public DatabaseBuilder or(String... conditions) {
        for(String condition : conditions) {
            this._sql.append(" " + condition);

            if(conditions[conditions.length - 1] != condition)
                this._sql.append(" OR");
        }

        this._sql.append("\n");
        return this;
    }

    public DatabaseBuilder groupBy(String... fields) {
        this._sql.append("GROUP BY").append(" ");

        for(String field : fields) {
            this._sql.append(field);

            if(fields[fields.length - 1] != field)
                this._sql.append(", ");
        }

        // Add new line to formation
        this._sql.append("\n");
        return this;
    }

    public DatabaseBuilder orderByAsc(String... fields) {
        this._sql.append("ORDER BY").append(" ");

        for(String field : fields) {
            this._sql.append(field);

            if(fields[fields.length - 1] != field)
                this._sql.append(", ");
            else
                this._sql.append(" ASC");
        }

        // Add new line to formation
        this._sql.append("\n");
        return this;
    }

    public DatabaseBuilder orderByDesc(String... fields) {
        this._sql.append("ORDER BY").append(" ");

        for(String field : fields) {
            this._sql.append(field);

            if(fields[fields.length - 1] != field)
                this._sql.append(", ");
            else
                this._sql.append(" DESC");
        }

        // Add new line to formation
        this._sql.append("\n");
        return this;
    }

    /**
     *
     * Custom queries -> "hard mode" :)
     * @param sql {String}
     *
     * @recommended for: Sub-queries or where clauses with priority
     */
    public DatabaseBuilder rawMode(String sql) {
        this._sql = new StringBuffer().append(sql);

        return this;
    }

    /**
     *
     * @return {String}
     *
     * returns the final and built sql
     */
    public String build() {
        return _sql.toString() + ";";
    }
}
