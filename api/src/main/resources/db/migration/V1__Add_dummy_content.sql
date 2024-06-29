DO
$$
    DECLARE
        new_user_id          uuid;
        new_course_id        uuid;
        dummy_tee_1_id       uuid;
        dummy_tee_2_id       uuid;
        i                    INTEGER;
        new_hole_id          uuid;
        dummy_tee_1_round_id uuid;
        dummy_tee_2_round_id uuid;
        random_score         INTEGER;
    BEGIN
        INSERT INTO users(first_name, last_name, email, initial_handicap)
        VALUES ('Dummy', 'User', 'dummy@user.com', 0)
        RETURNING id INTO new_user_id;

        INSERT INTO courses(name, location)
        VALUES ('Dummy Course', 'Dummy Location')
        RETURNING id INTO new_course_id;

        INSERT INTO tees(course_id, name, course_rating, slope_rating)
        VALUES (new_course_id, 'Dummy Tee 1', 0, 0)
        RETURNING id INTO dummy_tee_1_id;

        INSERT INTO tees(course_id, name, course_rating, slope_rating)
        VALUES (new_course_id, 'Dummy Tee 2', 0, 0)
        RETURNING id INTO dummy_tee_2_id;

        INSERT INTO rounds(user_id, course_id, tee_id)
        VALUES (new_user_id, new_course_id, dummy_tee_1_id)
        RETURNING id INTO dummy_tee_1_round_id;

        INSERT INTO rounds(user_id, course_id, tee_id)
        VALUES (new_user_id, new_course_id, dummy_tee_2_id)
        RETURNING id INTO dummy_tee_2_round_id;

        FOR i IN 1..18
            LOOP
                INSERT INTO holes(course_id, hole_number)
                VALUES (new_course_id, i)
                RETURNING id INTO new_hole_id;

                INSERT INTO holes_info(tee_id, hole_id, yardage, par, handicap)
                VALUES (dummy_tee_1_id, new_hole_id, 5, 5, 5);

                INSERT INTO holes_info(tee_id, hole_id, yardage, par, handicap)
                VALUES (dummy_tee_2_id, new_hole_id, floor(random() * (600 - 100 + 1) + 100), 3 + floor(random() * 3),
                        floor(random() * 18) + 1);

                random_score := floor(random() * 10) + 1;

                INSERT INTO scores(round_id, hole_id, score, adjusted_score)
                VALUES (dummy_tee_1_round_id, new_hole_id, random_score, random_score + floor(random() * 2) - 1);

                random_score := floor(random() * 10) + 1;

                INSERT INTO scores(round_id, hole_id, score, adjusted_score)
                VALUES (dummy_tee_2_round_id, new_hole_id, random_score, random_score + floor(random() * 2) - 1);
            END LOOP;
    END
$$;