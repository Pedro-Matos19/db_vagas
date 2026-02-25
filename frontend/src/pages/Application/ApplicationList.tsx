import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { deleteApplication, getApplications } from '../../services/applicationService';
import { type Application } from '../../types';

const ApplicationList: React.FC = () => {
  const [applications, setApplications] = useState<Application[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadApplications();
  }, []);

  const loadApplications = async () => {
    try {
      setLoading(true);
      const data = await getApplications();
      setApplications(data);
    } catch (err) {
      setError('Erro ao carregar candidaturas.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Tem certeza que deseja excluir esta candidatura?')) {
      try {
        await deleteApplication(id);
        setApplications(applications.filter((a) => a.id !== id));
      } catch (err) {
        alert('Erro ao excluir candidatura.');
        console.error(err);
      }
    }
  };

  if (loading) return <div className="p-4">Carregando...</div>;
  if (error) return <div className="p-4 text-red-500">{error}</div>;

  return (
    <div className="container mx-auto p-4">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Candidaturas</h1>
        <Link
          to="/applications/new"
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Nova Candidatura
        </Link>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {applications.map((application) => (
          <div
            key={application.id}
            className="bg-white p-6 rounded shadow-md border border-gray-200"
          >
            <h2 className="text-xl font-bold mb-2">Status: {application.status}</h2>
            <p className="text-gray-600 mb-1">
              <strong>Candidato:</strong> {application.candidate?.name}
            </p>
            <p className="text-gray-600 mb-1">
              <strong>Vaga:</strong> {application.job?.title}
            </p>
            <div className="flex justify-end space-x-2 mt-4">
              <Link
                to={`/applications/${application.id}`}
                className="text-yellow-600 hover:text-yellow-800"
              >
                Editar
              </Link>
              <button
                onClick={() => handleDelete(application.id!)}
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

export default ApplicationList;
