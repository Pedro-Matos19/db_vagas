import React from 'react';
import { Link } from 'react-router-dom';

const Home: React.FC = () => {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
      <div className="bg-white p-8 rounded shadow-md text-center">
        <h1 className="text-4xl font-bold text-blue-600 mb-4">
          Bem-vindo ao Sistema de Vagas
        </h1>
        <p className="text-lg text-gray-700">
          Conectando talentos às melhores oportunidades.
        </p>
        <div className="flex justify-center gap-2">
          <Link
            to="/candidates"
            className="mt-4 inline-block px-6 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
          >
            Ver Candidatos
          </Link>
          <Link
            to="/companies"
            className="mt-4 inline-block px-6 py-2 bg-green-600 text-white rounded-md hover:bg-green-700 gap-10"
          >
            Ver Empresas
          </Link>
          <Link
            to="/jobs"
            className="mt-4 inline-block px-6 py-2 bg-purple-600 text-white rounded-md hover:bg-purple-700"
          >
            Ver Vagas
          </Link>
          <Link
            to="/skills"
            className="mt-4 inline-block px-6 py-2 bg-orange-600 text-white rounded-md hover:bg-orange-700"
          >
            Ver Habilidades
          </Link>
          <Link
            to="/courses"
            className="mt-4 inline-block px-6 py-2 bg-teal-600 text-white rounded-md hover:bg-teal-700"
          >
            Ver Cursos
          </Link>
          <Link
            to="/applications"
            className="mt-4 inline-block px-6 py-2 bg-gray-800 text-white rounded-md hover:bg-gray-900"
          >
            Ver Candidaturas
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Home;
