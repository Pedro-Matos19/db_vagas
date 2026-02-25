import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { deleteJob, getJobs } from '../../services/jobService';
import { type Job } from '../../types';

const JobList: React.FC = () => {
  const [jobs, setJobs] = useState<Job[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadJobs();
  }, []);

  const loadJobs = async () => {
    try {
      setLoading(true);
      const data = await getJobs();
      setJobs(data);
    } catch (err) {
      setError('Erro ao carregar vagas.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Tem certeza que deseja excluir esta vaga?')) {
      try {
        await deleteJob(id);
        setJobs(jobs.filter((j) => j.id !== id));
      } catch (err) {
        alert('Erro ao excluir vaga.');
        console.error(err);
      }
    }
  };

  if (loading) return <div className="p-4">Carregando...</div>;
  if (error) return <div className="p-4 text-red-500">{error}</div>;

  return (
    <div className="container mx-auto p-4">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Vagas</h1>
        <Link
          to="/jobs/new"
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Nova Vaga
        </Link>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {jobs.map((job) => (
          <div
            key={job.id}
            className="bg-white p-6 rounded shadow-md border border-gray-200"
          >
            <h2 className="text-xl font-bold mb-2">{job.title}</h2>
            <p className="text-gray-600 mb-1">
              <strong>Tipo:</strong> {job.type}
            </p>
            <p className="text-gray-600 mb-1">
              <strong>Salário:</strong> {job.salary}
            </p>
            <p className="text-gray-600 mb-1">
              <strong>Status:</strong> {job.status}
            </p>
            <p className="text-gray-600 mb-1">
              <strong>Empresa:</strong> {job.company?.corporateName}
            </p>
            {job.requiredSkills?.length ? (
              <p className="text-gray-600 mb-1">
                <strong>Habilidades:</strong>{' '}
                {job.requiredSkills.map((s) => s.name).join(', ')}
              </p>
            ) : null}
            <div className="flex justify-end space-x-2 mt-4">
              <Link
                to={`/jobs/${job.id}`}
                className="text-yellow-600 hover:text-yellow-800"
              >
                Editar
              </Link>
              <button
                onClick={() => handleDelete(job.id!)}
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

export default JobList;
