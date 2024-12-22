import java.util.*;
import java.util.stream.*;
import java.util.concurrent.TimeUnit;

class Student{
    int studentId;
    String name;
    String group;
    List<Course> courses;

    public Student(int studentId, String name, String group, List<Course> courses){
        this.studentId=studentId;
        this.name=name;
        this.group=group;
        this.courses=courses;
    }

    public int getStudentId(){
        return studentId;
    }

    public String getName(){
        return name;
    }

    public String getGroup(){
        return group;
    }

    public List<Course> getCourses(){
        return courses;
    }
}

class Course{
    int courseId;
    int creditPoints;
    int year;
    String fullName;
    List<Student> students;

    public Course(int courseId, int creditPoints, int year, String fullName, List<Student> students){
        this.courseId=courseId;
        this.creditPoints=creditPoints;
        this.year=year;
        this.fullName=fullName;
        this.students=students;
    }

    public int getCourseId(){
        return courseId;
    }

    public int getCreditPoints(){
        return creditPoints;
    }

    public List<Student> getStudents(){
        return students;
    }
}

public class lab7{

    // Exercise 1
    public List<Integer> exercise1(List<Integer> integers){
        return integers.stream()
                       .filter(x -> x % 2 == 0)
                       .map(x -> x * x)
                       .toList();
    }

    // Exercise 2
    public int exercise2(List<Integer> integers){
        return integers.stream()
                       .map(x -> x * x)
                       .reduce(0, Integer::sum);
    }

    // Exercise 3a
    public Map<Integer, Long> exercise3a(List<String> strings){
        return strings.stream()
                      .collect(Collectors.groupingBy(String::length, Collectors.counting()));
    }

    // Exercise 3b
    public Map<Integer, Integer> exercise3b(List<String> strings){
        return strings.stream()
                      .reduce(new HashMap<>(),
                              (map, str) -> {
                                  map.put(str.length(), map.getOrDefault(str.length(), 0) + 1);
                                  return map;
                              },
                              (map1, map2) -> {
                                  map2.forEach((k, v) -> map1.merge(k, v, Integer::sum));
                                  return map1;
                              });
    }

    // Exercise 4
    public List<String> exercise4(List<List<String>> listOfLists){
        return listOfLists.stream()
                          .flatMap(Collection::stream)
                          .distinct()
                          .toList();
    }

    // Exercise 5
    public String exercise5(List<Student> students, String group){
        return students.stream()
                       .filter(s -> s.getGroup().equals(group))
                       .map(Student::getName)
                       .collect(Collectors.joining(", "));
    }

    // Exercise 6
    public long exercise6(List<Integer> integers, boolean useParallel){
        return (useParallel ? integers.parallelStream() : integers.stream())
               .mapToLong(x -> (long) x * x)
               .sum();
    }

    // Exercise 7
    public List<Student> exercise7(List<Student> students){
        return students.stream()
                       .filter(s -> s.getCourses().stream()
                                     .anyMatch(c -> c.getCreditPoints() >= 5))
                       .toList();
    }

    // Exercise 8
    public List<Student> exercise8(List<Student> students){
        return students.stream()
                       .filter(s -> s.getCourses().stream()
                                     .mapToInt(Course::getCreditPoints).sum() > 30)
                       .toList();
    }

    // Exercise 9
    public Map<Integer, List<Integer>> exercise9(List<Course> courses){
        return courses.stream()
                      .collect(Collectors.toMap(
                          Course::getCourseId,
                          c -> c.getStudents().stream()
                                .map(Student::getStudentId)
                                .toList()
                      ));
    }

    // Exercise 10
    public List<Student> exercise10(List<Student> students, String group){
        return students.stream()
                       .filter(s -> s.getGroup().equals(group))
                       .sorted(Comparator.comparing(Student::getName))
                       .limit(6)
                       .toList();
    }

    // Exercise 11
    public Map<String, List<Course>> exercise11(List<Student> students){
        return students.stream()
                       .collect(Collectors.groupingBy(
                           Student::getGroup,
                           Collectors.flatMapping(
                               s -> s.getCourses().stream(),
                               Collectors.toSet()
                           )
                       ))
                       .entrySet()
                       .stream()
                       .collect(Collectors.toMap(
                           Map.Entry::getKey,
                           e -> new ArrayList<>(e.getValue())
                       ));
    }

    public static void main(String[] args){
        lab7 exercises=new lab7();

        // Exercise 1
        List<Integer> input1=Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println("Exercise 1: " + exercises.exercise1(input1));

        // Exercise 2
        System.out.println("Exercise 2: " + exercises.exercise2(input1));

        // Exercise 3
        List<String> strings=Arrays.asList("glass", "wood", "concrete", "metal", "steel", "plastic", "aluminium", "granite");
        System.out.println("Exercise 3a: " + exercises.exercise3a(strings));
        System.out.println("Exercise 3b: " + exercises.exercise3b(strings));

        // Exercise 4
        List<List<String>> listOfLists=Arrays.asList(
            Arrays.asList("Porsche 911", "Porsche Taycan", "Mercedes S Class", "Mercedes G Class", "BMW 7 Series", "BMW M8"),
            Arrays.asList("Porsche 911", "Mercedes S Class", "Audi RS7", "Ferrari Roma")
        );
        System.out.println("Exercise 4: " + exercises.exercise4(listOfLists));

        // Exercise 5
        List<Student> stud=Arrays.asList(
            new Student(1, "Gabi", "B", null),
            new Student(2, "Ionut", "B", null),
            new Student(3, "Andrei", "B", null),
            new Student(4, "Luana", "B", null),
            new Student(5, "Denisa", "B", null),
            new Student(6, "Bianca", "B", null),
            new Student(7, "Vlad", "A", null),
            new Student(8, "Genan", "A", null),
            new Student(9, "Remus", "A", null),
            new Student(10, "Ana", "A", null)
        );
        System.out.println("Exercise 5: " + exercises.exercise5(stud, "B"));

        // Exercise 6
        List<Integer> largeList=IntStream.range(1, 100000001).boxed().toList();
        long startTime = System.nanoTime();
        System.out.println("Exercise 6: ");
        System.out.println("Sequential sum: " + exercises.exercise6(largeList, false));
        System.out.println("Sequential time: " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) + " ms");

        startTime = System.nanoTime();
        System.out.println("Parallel sum:   " + exercises.exercise6(largeList, true));
        System.out.println("Parallel time:   " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime) + " ms");

        List<Course> courses=new ArrayList<>();
        List<Student> students=new ArrayList<>();

        Course course1=new Course(1, 5, 2023, "Special Mathematics", new ArrayList<>());
        Course course2=new Course(2, 5, 2023, "PTMS", new ArrayList<>());
        Course course3=new Course(3, 4, 2023, "Physics", new ArrayList<>());
        Course course4=new Course(4, 5, 2023, "Databases", new ArrayList<>());
        Course course5=new Course(5, 5, 2023, "OOP", new ArrayList<>());
        Course course6=new Course(6, 4, 2023, "EDAE", new ArrayList<>());
        Course course7=new Course(7, 4, 2023, "Electronic devices", new ArrayList<>());
        Course course8=new Course(8, 4, 2023, "Algebra", new ArrayList<>());
        Course course9=new Course(9, 5, 2023, "Discrete Mathematics", new ArrayList<>());
        Course course10=new Course(10, 4, 2023, "Computer Networks", new ArrayList<>());
        Course course11=new Course(11, 5, 2023, "Operating Systems", new ArrayList<>());
        Course course12=new Course(12, 3, 2023, "Computer Architecture", new ArrayList<>());
        Course course13=new Course(13, 4, 2023, "FEE", new ArrayList<>());
        Course course14=new Course(14, 4, 2023, "Data Aquisition", new ArrayList<>());
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        courses.add(course6);
        courses.add(course7);
        courses.add(course8);
        courses.add(course9);
        courses.add(course10);
        courses.add(course11);
        courses.add(course12);
        courses.add(course13);
        courses.add(course14);

        Student student1=new Student(1, "Gabi", "B", Arrays.asList(course1, course2, course3, course4, course5, course6, course7, course9, course10, course11, course12, course13));
        Student student2=new Student(2, "Ionut", "B", Arrays.asList(course1, course2, course3, course4, course5, course6, course7, course9, course10, course11, course12, course13));
        Student student3=new Student(3, "Andrei", "B", Arrays.asList(course1, course2, course3, course4, course5, course6, course7, course9, course10, course11, course12, course13));
        Student student4=new Student(4, "Luana", "B", Arrays.asList(course1, course2, course3, course4, course5, course6, course7, course9, course10, course11, course12, course13));
        Student student5=new Student(5, "Denisa", "B", Arrays.asList(course1, course2, course3, course4, course5, course6, course7, course9, course10, course11, course12, course13));
        Student student6=new Student(6, "Bianca", "B", Arrays.asList(course1, course2, course3, course4, course5, course6, course7, course9, course10, course11, course12, course13));
        Student student7=new Student(7, "Vlad", "A", Arrays.asList(course1, course2, course3, course5, course7, course8, course10, course11, course12, course14));
        Student student8=new Student(8, "Genan", "A", Arrays.asList(course1, course2, course3, course5, course7, course8, course10, course11, course12, course14));
        Student student9=new Student(9, "Remus", "A", Arrays.asList(course1, course2, course3, course5, course7, course8, course10, course11, course12, course14));
        Student student10=new Student(10, "Ana", "A", Arrays.asList(course1, course2, course3, course5, course7, course8, course10, course11, course12, course14));

        course1.students.addAll(Arrays.asList(student1, student2, student3, student4, student5, student6, student7, student8, student9, student10));
        course2.students.addAll(Arrays.asList(student1, student2, student3, student4, student5, student6, student7, student8, student9, student10));
        course3.students.addAll(Arrays.asList(student1, student2, student3, student4, student5, student6, student7, student8, student9, student10));
        course4.students.addAll(Arrays.asList(student1, student2, student3, student4, student5, student6));
        course5.students.addAll(Arrays.asList(student1, student2, student3, student4, student5, student6, student7, student8, student9, student10));
        course6.students.addAll(Arrays.asList(student1, student2, student3, student4, student5, student6));
        course7.students.addAll(Arrays.asList(student1, student2, student3, student4, student5, student6, student7, student8, student9, student10));
        course8.students.addAll(Arrays.asList(student7, student8, student9, student10));
        course9.students.addAll(Arrays.asList(student1, student2, student3, student4, student5, student6));
        course10.students.addAll(Arrays.asList(student1, student2, student3, student4, student5, student6, student7, student8, student9, student10));
        course11.students.addAll(Arrays.asList(student1, student2, student3, student4, student5, student6, student7, student8, student9, student10));
        course12.students.addAll(Arrays.asList(student1, student2, student3, student4, student5, student6, student7, student8, student9, student10));
        course13.students.addAll(Arrays.asList(student1, student2, student3, student4, student5, student6));
        course14.students.addAll(Arrays.asList(student7, student8, student9, student10));
        students.addAll(Arrays.asList(student1, student2, student3, student4, student5, student6, student7, student8, student9, student10));

        // Exercise 7
        System.out.println("Exercise 7: " +
            exercises.exercise7(students).stream()
                     .map(Student::getName)
                     .toList());

        // Exercise 8
        System.out.println("Exercise 8: " +
            exercises.exercise8(students).stream()
                     .map(Student::getName)
                     .toList());

        // Exercise 9
        System.out.println("Exercise 9: " + exercises.exercise9(courses));

        // Exercise 10
        System.out.println("Exercise 10: " +
            exercises.exercise10(students, "B").stream()
                     .map(Student::getName)
                     .toList());

        // Exercise 11
        System.out.println("Exercise 11: " +
            exercises.exercise11(students).entrySet().stream()
                     .map(e -> e.getKey() + "=" + e.getValue().stream()
                                                  .map(Course::getCourseId)
                                                  .toList())
                     .toList());
    }
}