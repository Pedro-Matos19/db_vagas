import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getApplicationById, updateApplication, createApplication } from '../../services/applicationService';
import { getCandidates } from '../../services/candidateService';
import { getJobs } from '../../services/jobService';
import { type Application, type Candidate, type Job } from '../../types';

const ApplicationForm: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [application, setApplication] = useState<Partial<Application>>({
    status: 'Em análise',
  });
  const [candidates, setCandidates] = useState<Candidate[]>([]);
  const [jobs, setJobs] = useState<Job[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (id) {
      loadApplication(Number(id));
    } else {
      loadDependencies();
    }
  }, [id]);

  const loadDependencies = async () => {
    try {
      setLoading(true);
      const [candidatesData, jobsData] = await Promise.all([
        getCandidates(),
        getJobs(),
      ]);
      setCandidates(candidatesData);
      setJobs(jobsData);
    } catch (err) {
      setError('Erro ao carregar listas de candidatos e vagas.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const loadApplication = async (appId: number) => {
    try {
      setLoading(true);
      const data = await getApplicationById(appId);
      setApplication(data);
    } catch (err) {
      setError('Erro ao carregar candidatura.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const { name, value } = e.target;
    setApplication((prev) => ({ ...prev, [name]: value }));
  };

  const handleRelationChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const { name, value } = e.target;
    if (name === 'candidateId') {
      setApplication((prev) => ({ 
        ...prev, 
        candidate: { id: Number(value) } as Candidate 
      }));
    } else if (name === 'jobId') {
      setApplication((prev) => ({ 
        ...prev, 
        job: { id: Number(value) } as Job 
      }));
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      if (id) {
        if (!application.id) return; 
        await updateApplication(Number(id), application as Application);
      } else {
        await createApplication(application as Application);
      }
      navigate('/applications');
    } catch (err) {
      setError('Erro ao salvar candidatura.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  if (loading && id) return <div className="p-4">Carregando...</div>;
  if (!application && id) return <div className="p-4">Candidatura não encontrada.</div>;

  return (
    <div className="container mx-auto p-4 max-w-2xl">
      <h1 className="text-2xl font-bold mb-6">
        {id ? `Editar Candidatura #${id}` : 'Nova Candidatura'}
      </h1>

      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
          {error}
        </div>
      )}

      <form onSubmit={handleSubmit} className="bg-white p-6 rounded shadow-md space-y-4">
        
        {/* Se for edição, mostra apenas texto. Se for criação, mostra selects */}
        {id ? (
          <div className="mb-4">
            <p><strong>Candidato:</strong> {application.candidate?.name}</p>
            <p><strong>Vaga:</strong> {application.job?.title}</p>
            <p><strong>Empresa:</strong> {application.job?.company?.corporateName}</p>
          </div>
        ) : (
          <>
            <div>
              <label className="block text-gray-700 font-bold mb-2" htmlFor="candidateId">
                Candidato
              </label>
              <select
                id="candidateId"
                name="candidateId"
                onChange={handleRelationChange}
                className="w-full border border-gray-300 px-3 py-2 rounded focus:outline-none focus:border-blue-500"
                required
                defaultValue=""
              >
                <option value="" disabled>Selecione um candidato</option>
                {candidates.map((c) => (
                  <option key={c.id} value={c.id}>
                    {c.name} (CPF: {c.cpf})
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label className="block text-gray-700 font-bold mb-2" htmlFor="jobId">
                Vaga
              </label>
              <select
                id="jobId"
                name="jobId"
                onChange={handleRelationChange}
                className="w-full border border-gray-300 px-3 py-2 rounded focus:outline-none focus:border-blue-500"
                required
                defaultValue=""
              >
                <option value="" disabled>Selecione uma vaga</option>
                {jobs.map((j) => (
                  <option key={j.id} value={j.id}>
                    {j.title} - {j.company?.corporateName}
                  </option>
                ))}
              </select>
            </div>
          </>
        )}

        <div>
          <label className="block text-gray-700 font-bold mb-2" htmlFor="status">
            Status da Candidatura
          </label>
          <select
            id="status"
            name="status"
            value={application.status || 'Em análise'}
            onChange={handleChange}
            className="w-full border border-gray-300 px-3 py-2 rounded focus:outline-none focus:border-blue-500"
          >
            <option value="Pendente">Pendente</option>
            <option value="Em análise">Em análise</option>
            <option value="Entrevista Agendada">Entrevista Agendada</option>
            <option value="Aprovado">Aprovado</option>
            <option value="Rejeitado">Rejeitado</option>
          </select>
        </div>

        <div className="flex justify-end space-x-2 pt-4">
          <button
            type="button"
            onClick={() => navigate('/applications')}
            className="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600"
          >
            Cancelar
          </button>
          <button
            type="submit"
            disabled={loading}
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 disabled:bg-blue-300"
          >
            {loading ? 'Salvando...' : 'Salvar'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default ApplicationForm;
