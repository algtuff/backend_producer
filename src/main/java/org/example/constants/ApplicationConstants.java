package org.example.constants;

public class ApplicationConstants {

    public static final String PRODUCERS = "/producers";
    public static final String WINNER = "/winner";
    public static final String MIN_MAX_INTERVAL = "/min-max-interval";
    public static final String BY_ID = "/{id}";
    public static final String MOVIES = "/movies";
    public static final String CSV_FILE = "movielist.csv";
    public static final Integer YEAR_INDEX = 0;
    public static final Integer TITLE_INDEX = 1;
    public static final Integer STUDIO_INDEX = 2;
    public static final Integer PRODUCERS_INDEX = 3;
    public static final Integer WINNER_INDEX = 4;
    public static final String COLUMN_MAX_YEAR = "following_win";
    public static final String COLUMN_MIN_YEAR = "previous_win";
    public static final String COLUMN_INTER = "diff";
    public static final String COLUMN_NAME = "producer_name";
    public static final String CSV_SPLITTER = ";";
    public static final String MSG_MIGRATING_DATA ="Migrating data from CSV file to database...";
    public static final String MSG_DATA_MIGRATED = "Data migrated successfully!";
    public static final String EXCEPTION_MOVIE_NOT_FOUND = "Movie not found";
    public static final String EXCEPTION_PRODUCER_NOT_FOUND = "Producer not found";

    public static final String QUERY_WINNER_MAX_INTERVAL = """
        WITH victory_year AS (
            SELECT
                p.id AS producer_id,
                p.name AS producer_name,
                m.movie_year,
                ROW_NUMBER() OVER (PARTITION BY p.id ORDER BY m.movie_year) AS vitoria_num
            FROM
                producer p
                    JOIN
                movie_producers mp ON p.id = mp.producers_id
                    JOIN
                movie m ON mp.movie_movie_id = m.movie_id
            WHERE
                m.winner = true
        ),
        differences AS (
             SELECT
                 producer_id,
                 producer_name,
                 movie_year,
                 LAG(movie_year) OVER (PARTITION BY producer_id ORDER BY movie_year) AS previous_win
             FROM
                 victory_year
        ),
        intervals AS (
            SELECT
                 producer_id,
                 producer_name,
                 movie_year,
                 previous_win,
                 movie_year - previous_win AS inter
            FROM
                 differences
            WHERE
                 previous_win IS NOT NULL
        )
        SELECT
            producer_name,
            movie_year as following_win,
            previous_win,
            max(inter) as diff
        FROM
            intervals
        group by
            producer_id,
            producer_name,
            movie_year,
            previous_win
        having diff = select max(inter) from intervals
        """;

    public static final String QUERY_WINNER_MIN_INTERVAL = """
        WITH victory_year AS (
            SELECT
                p.id AS producer_id,
                p.name AS producer_name,
                m.movie_year,
                ROW_NUMBER() OVER (PARTITION BY p.id ORDER BY m.movie_year) AS vitoria_num
            FROM
                producer p
                    JOIN
                movie_producers mp ON p.id = mp.producers_id
                    JOIN
                movie m ON mp.movie_movie_id = m.movie_id
            WHERE
                m.winner = true
        ),
        differences AS (
             SELECT
                 producer_id,
                 producer_name,
                 movie_year,
                 LAG(movie_year) OVER (PARTITION BY producer_id ORDER BY movie_year) AS previous_win
             FROM
                 victory_year
        ),
        intervals AS (
            SELECT
                 producer_id,
                 producer_name,
                 movie_year,
                 previous_win,
                 movie_year - previous_win AS inter
            FROM
                 differences
            WHERE
                 previous_win IS NOT NULL
        )
        SELECT
            producer_name,
            movie_year as following_win,
            previous_win,
            min(inter) as diff
        FROM
            intervals
        group by
            producer_id,
            producer_name,
            movie_year,
            previous_win
        having diff = select min(inter) from intervals""";


    private ApplicationConstants() {
        throw new IllegalStateException("Utility class");
    }
}
