import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { deleteApplication, getApplications } from '../../services/applicationService';
import { createInterview } from '../../services/interviewService';
import { type Application, type InterviewRequest } from '../../types';

const ApplicationList: React.FC = () => {
  const [applications, setApplications] = useState<Application[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const [showInterviewModal, setShowInterviewModal] = useState(false);
  const [selectedApplicationId, setSelectedApplicationId] = useState<number | null>(null);
  const [interviewData, setInterviewData] = useState({
    dateTime: '',
    location: '',
  });

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

  const handleOpenInterviewModal = (appId: number) => {
    setSelectedApplicationId(appId);
    setInterviewData({ dateTime: '', location: '' });
    setShowInterviewModal(true);
  };

  const handleScheduleInterview = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!selectedApplicationId) return;

    try {
      const interviewRequest: InterviewRequest = {
        application: { id: selectedApplicationId },
        dateTime: new Date(interviewData.dateTime).toISOString(),
        location: interviewData.location,
        status: 'AGENDADA',
        feedback: '',
      };

      await createInterview(interviewRequest);

      setShowInterviewModal(false);
      alert('Entrevista agendada com sucesso! O status da candidatura será atualizado automaticamente.');
      loadApplications();
    } catch (err) {
      alert('Erro ao agendar entrevista.');
      console.error(err);
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
              {application.status !== 'Em Entrevista' && (
                <button
                  onClick={() => handleOpenInterviewModal(application.id!)}
                  className="bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700 text-sm"
                >
                  Agendar Entrevista
                </button>
              )}
              <Link
                to={`/applications/${application.id}`}
                className="text-yellow-600 hover:text-yellow-800 px-3 py-1"
              >
                Editar
              </Link>
              <button
                onClick={() => handleDelete(application.id!)}
                className="text-red-600 hover:text-red-800 px-3 py-1"
              >
                Excluir
              </button>
            </div>
          </div>
        ))}
      </div>

      {showInterviewModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-white p-6 rounded shadow-lg w-96">
            <h2 className="text-xl font-bold mb-4">Agendar Entrevista</h2>
            <form onSubmit={handleScheduleInterview}>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Data e Hora</label>
                <input
                  type="datetime-local"
                  required
                  className="w-full border p-2 rounded"
                  value={interviewData.dateTime}
                  onChange={(e) => setInterviewData({ ...interviewData, dateTime: e.target.value })}
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Local / Link</label>
                <input
                  type="text"
                  required
                  className="w-full border p-2 rounded"
                  value={interviewData.location}
                  onChange={(e) => setInterviewData({ ...interviewData, location: e.target.value })}
                />
              </div>
              <div className="flex justify-end space-x-2">
                <button
                  type="button"
                  onClick={() => setShowInterviewModal(false)}
                  className="bg-gray-300 px-4 py-2 rounded hover:bg-gray-400"
                >
                  Cancelar
                </button>
                <button
                  type="submit"
                  className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
                >
                  Salvar
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default ApplicationList;
