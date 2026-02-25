import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { deleteCourse, getCourses } from '../../services/courseService';
import { type Course } from '../../types';

const CourseList: React.FC = () => {
  const [courses, setCourses] = useState<Course[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadCourses();
  }, []);

  const loadCourses = async () => {
    try {
      setLoading(true);
      const data = await getCourses();
      setCourses(data);
    } catch (err) {
      setError('Erro ao carregar cursos.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Tem certeza que deseja excluir este curso?')) {
      try {
        await deleteCourse(id);
        setCourses(courses.filter((c) => c.id !== id));
      } catch (err) {
        alert('Erro ao excluir curso.');
        console.error(err);
      }
    }
  };

  if (loading) return <div className="p-4">Carregando...</div>;
  if (error) return <div className="p-4 text-red-500">{error}</div>;

  return (
    <div className="container mx-auto p-4">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Cursos</h1>
        <Link
          to="/courses/new"
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Novo Curso
        </Link>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {courses.map((course) => (
          <div
            key={course.id}
            className="bg-white p-6 rounded shadow-md border border-gray-200"
          >
            <h2 className="text-xl font-bold mb-2">{course.name}</h2>
            <p className="text-gray-600 mb-1">
              <strong>Nível:</strong> {course.level}
            </p>
            <div className="flex justify-end space-x-2 mt-4">
              <Link
                to={`/courses/${course.id}`}
                className="text-yellow-600 hover:text-yellow-800"
              >
                Editar
              </Link>
              <button
                onClick={() => handleDelete(course.id!)}
                className="text-red-600 hover:text-red-800"
              >
                Excluir
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CourseList;
