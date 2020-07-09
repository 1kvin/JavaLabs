package ru.spbstu.main.shapes;

/**
 * Представление о треугольнике.
 * <p>
 * Треуго́льник (в евклидовом пространстве) — геометрическая
 * фигура, образованная тремя отрезками, которые соединяют
 * три точки, не лежащие на одной прямой. Указанные три
 * точки называются вершинами треугольника, а отрезки —
 * сторонами треугольника. Часть плоскости, ограниченная
 * сторонами, называется внутренностью треугольника: нередко
 * треугольник рассматривается вместе со своей внутренностью
 * (например, для определения понятия площади).
 *
 * @see <a href="https://ru.wikipedia.org/wiki/%D0%A2%D1%80%D0%B5%D1%83%D0%B3%D0%BE%D0%BB%D1%8C%D0%BD%D0%B8%D0%BA">Треугольник</a>
 */
public class Triangle implements Shape, Polygon {
    private Point point1, point2, point3;
    private int angle;
    private double a, b, c;

    public Triangle(Point point1, Point point2, Point point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        angle = 0;
        CalcLength();
    }

    public Triangle(Point point1, Point point2, Point point3, int angle) {
        this(point1, point2, point3);
        this.angle = angle;
    }

    private void CalcLength() {
        double a = Math.sqrt(Math.abs(Math.pow(point1.getX() - point2.getX(), 2) + Math.pow(point1.getY() - point2.getY(), 2)));
        double b = Math.sqrt(Math.abs(Math.pow(point1.getX() - point3.getX(), 2) + Math.pow(point1.getY() - point3.getY(), 2)));
        double c = Math.sqrt(Math.abs(Math.pow(point2.getX() - point3.getX(), 2) + Math.pow(point2.getY() - point3.getY(), 2)));
    }

    @Override
    public float getArea() {
        double pp = getPerimeter() / 2;

        return (float) Math.sqrt(pp * (pp - a) * (pp - b) * (pp - c));
    }

    @Override
    public int getRotation() {
        return angle;
    }

    @Override
    public float getPerimeter() {
        return (float) (a + b + c);
    }

    /*
     * TODO: Реализовать класс 'Triangle'
     * 1. Используйте наследование.
     * 2. Реализуйте все абстрактные методы.
     */
}
