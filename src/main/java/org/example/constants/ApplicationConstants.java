package org.example.constants;

public class ApplicationConstants {

    public static final String PRODUCER = "/producer";
    public static final String CSV_FILE = "movielist.csv";
    public static final Integer YEAR_INDEX = 0;
    public static final Integer TITLE_INDEX = 1;
    public static final Integer STUDIO_INDEX = 2;
    public static final Integer PRODUCERS_INDEX = 3;
    public static final Integer WINNER_INDEX = 4;
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
                    producer.id in (select p.id
                                    from producer as p
                                             join movie_producers as m_p on p.id = m_p.producers_id
                                             join movie as m on m_p.movie_movie_id = m.movie_id
                                    group by p.id
                                    having count(m.movie_id) > 1)
                group by producer.name
                having inter = (select max(inter) from
                    (select max(m.movie_year)- min(m.movie_year) as inter
                     from producer as p
                              join movie_producers as m_p on p.id = m_p.producers_id
                              join movie as m on m_p.movie_movie_id = m.movie_id
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
                    producer.id in (select p.id
                                    from producer as p
                                             join movie_producers as m_p on p.id = m_p.producers_id
                                             join movie as m on m_p.movie_movie_id = m.movie_id
                                    group by p.id
                                    having count(m.movie_id) > 1)
                group by producer.name
                having inter = (select min(inter) from
                    (select max(m.movie_year)- min(m.movie_year) as inter
                     from producer as p
                              join movie_producers as m_p on p.id = m_p.producers_id
                              join movie as m on m_p.movie_movie_id = m.movie_id
                     group by p.id
                     having count(m.movie_id) > 1))""";


    private ApplicationConstants() {
        throw new IllegalStateException("Utility class");
    }
}
