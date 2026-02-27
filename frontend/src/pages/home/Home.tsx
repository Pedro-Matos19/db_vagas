import React from 'react';
import { Link } from 'react-router-dom';

interface DashboardCardProps {
  title: string;
  description: string;
  to: string;
  color: string;
  icon: React.ReactNode;
}

const DashboardCard: React.FC<DashboardCardProps> = ({ title, description, to, color, icon }) => (
  <Link
    to={to}
    className={`block p-6 bg-white rounded-lg border border-gray-200 shadow-md hover:bg-gray-50 transition-all duration-300 transform hover:-translate-y-1 hover:shadow-xl group`}
  >
    <div className={`w-12 h-12 rounded-full flex items-center justify-center mb-4 ${color} text-white`}>
      {icon}
    </div>
    <h5 className="mb-2 text-2xl font-bold tracking-tight text-gray-900 group-hover:text-blue-700">
      {title}
    </h5>
    <p className="font-normal text-gray-700">
      {description}
    </p>
  </Link>
);

const Home: React.FC = () => {
  return (
    <div className="space-y-8">
      <div className="text-center py-10 bg-gradient-to-r from-blue-600 to-indigo-700 rounded-2xl shadow-xl text-white mb-10">
        <h1 className="text-4xl md:text-5xl font-extrabold mb-4">
          Bem-vindo ao Vagas<span className="text-yellow-400">DB</span>
        </h1>
        <p className="text-xl md:text-2xl opacity-90 max-w-2xl mx-auto">
          Gerencie candidatos, empresas e oportunidades em um só lugar.
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <DashboardCard
          title="Candidatos"
          description="Gerencie perfis de candidatos, informações pessoais e currículos."
          to="/candidates"
          color="bg-blue-600"
          icon={
            <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
            </svg>
          }
        />
        
        <DashboardCard
          title="Empresas"
          description="Cadastre empresas parceiras e gerencie suas informações corporativas."
          to="/companies"
          color="bg-green-600"
          icon={
            <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4" />
            </svg>
          }
        />

        <DashboardCard
          title="Vagas"
          description="Publique e gerencie oportunidades de emprego e estágios."
          to="/jobs"
          color="bg-purple-600"
          icon={
            <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 13.255A23.931 23.931 0 0112 15c-3.183 0-6.22-.62-9-1.745M16 6V4a2 2 0 00-2-2h-4a2 2 0 00-2 2v2m4 6h.01M5 20h14a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
            </svg>
          }
        />

        <DashboardCard
          title="Candidaturas"
          description="Acompanhe o status das candidaturas e processos seletivos."
          to="/applications"
          color="bg-gray-800"
          icon={
            <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-3 7h3m-3 4h3m-6-4h.01M9 16h.01" />
            </svg>
          }
        />

        <DashboardCard
          title="Habilidades"
          description="Mantenha o catálogo de competências técnicas e comportamentais."
          to="/skills"
          color="bg-orange-600"
          icon={
            <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
            </svg>
          }
        />

        <DashboardCard
          title="Cursos"
          description="Registre cursos e formações para enriquecer os currículos."
          to="/courses"
          color="bg-teal-600"
          icon={
            <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253" />
            </svg>
          }
        />
      </div>
    </div>
  );
};

export default Home;
