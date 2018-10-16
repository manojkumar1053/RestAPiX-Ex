package com.teamtreehouse.core;

import com.teamtreehouse.course.Course;
import com.teamtreehouse.course.CourseRepository;
import com.teamtreehouse.review.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DatabaseLoader implements ApplicationRunner {
    private final CourseRepository courses;

    @Autowired
    public DatabaseLoader(CourseRepository courses) {
        this.courses = courses;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Course course = new Course("Java Basics", "https://teamtreehouse.com/l/java");
        course.addReview(new Review(3, "You Are Awesome !!!"));
        courses.save(course);
        String[] templates = {
                "Up and runnig with %s",
                "%s basics",
                "%s for beginners",
                "%s for neckbeards",
                "Under the hood"
        };

        String[] buzzwords = {
                "Spring Rest Data",
                "Java 9",
                "Scala",
                "Hibernate"

        };

        List<Course> bunchOfCourses = new ArrayList<>();
        IntStream.range(0, 100)
                .forEach(i -> {
                    String template = templates[i % templates.length];
                    String buzzword = buzzwords[i % buzzwords.length];
                    String title = String.format(template, buzzword);
                    Course c = new Course(title, "http://test");
                    c.addReview(new Review(i % 5, String.format("more %s pleased!!!!", buzzword)));
                    bunchOfCourses.add(c);
                });
        courses.saveAll(bunchOfCourses);
    }
}
