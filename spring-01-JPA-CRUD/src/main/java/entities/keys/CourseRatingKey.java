package entities.keys;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;

import java.util.Objects;

@Embeddable
@AllArgsConstructor
public class CourseRatingKey {

    @JoinColumn(name = "student_id")
    private int studentId;

    @JoinColumn(name = "course_id")
    private int courseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseRatingKey that = (CourseRatingKey) o;
        return studentId == that.studentId && courseId == that.courseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }
}
