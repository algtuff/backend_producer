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
    public static final String COLUMN_MAX_YEAR = "max_year";
    public static final String COLUMN_MIN_YEAR = "min_year";
    public static final String COLUMN_INTER = "inter";
    public static final String COLUMN_NAME = "name";
    public static final String CSV_SPLITTER = ";";
    public static final String MSG_MIGRATING_DATA ="Migrating data from CSV file to database...";
    public static final String MSG_DATA_MIGRATED = "Data migrated successfully!";
    public static final String EXCEPTION_MOVIE_NOT_FOUND = "Movie not found";
    public static final String EXCEPTION_PRODUCER_NOT_FOUND = "Producer not found";

    public static final String QUERY_WINNER_MAX_INTERVAL = """
                select
                        producer.name,
                        max(movie.movie_year) as max_year,
                        min(movie.movie_year) as min_year,
                        max(movie.movie_year) -min(movie.movie_year) as inter
                from producer as producer
                         join movie_producers as mp on producer.id = mp.producers_id
                         join movie as movie on mp.movie_movie_id = movie.movie_id
                where
                    movie.winner = true and
                    producer.id in (select p.id
                                        from producer as p
                                             join movie_producers as m_p on p.id = m_p.producers_id
                                             join movie as m on m_p.movie_movie_id = m.movie_id
                                        where m.winner = true
                                    group by p.id
                                    having count(m.movie_id) > 1)
                group by producer.name
                having inter = (select max(inter) from
                    (select max(m.movie_year)- min(m.movie_year) as inter
                        from producer as p
                              join movie_producers as m_p on p.id = m_p.producers_id
                              join movie as m on m_p.movie_movie_id = m.movie_id
                        where m.winner = true
                     group by p.id
                     having count(m.movie_id) > 1))""";

    public static final String QUERY_WINNER_MIN_INTERVAL = """
                select
                    producer.name,
                    max(movie.movie_year) as max_year,
                    min(movie.movie_year) as min_year,
                    max(movie.movie_year) -min(movie.movie_year) as inter
                from producer as producer
                         join movie_producers as mp on producer.id = mp.producers_id
                         join movie as movie on mp.movie_movie_id = movie.movie_id
                where
                    movie.winner = true and
                    producer.id in (select p.id
                                        from producer as p
                                             join movie_producers as m_p on p.id = m_p.producers_id
                                             join movie as m on m_p.movie_movie_id = m.movie_id
                                        where m.winner = true
                                    group by p.id
                                    having count(m.movie_id) > 1)
                group by producer.name
                having inter = (select min(inter) from
                    (select max(m.movie_year)- min(m.movie_year) as inter
                        from producer as p
                              join movie_producers as m_p on p.id = m_p.producers_id
                              join movie as m on m_p.movie_movie_id = m.movie_id
                        where m.winner = true
                     group by p.id
                     having count(m.movie_id) > 1))""";


    private ApplicationConstants() {
        throw new IllegalStateException("Utility class");
    }
}
