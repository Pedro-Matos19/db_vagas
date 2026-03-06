import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { createJob, getJobById, updateJob } from '../../services/jobService';
import { getCompanies } from '../../services/companyService';
import { getSkills } from '../../services/skillService';
import { type JobRequest, type Company, type Skill } from '../../types';

const jobStatuses = {
  'Aberta': 'ABERTA',
  'Fechada': 'FECHADA',
  'Em análise': 'EM_ANALISE'
}

const jobStatusesReverse = {
  'ABERTA': 'Aberta',
  'FECHADA': 'Fechada',
  'EM_ANALISE': 'Em análise'
}

const JobForm: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [job, setJob] = useState<JobRequest>({
    title: '',
    description: '',
    type: '',
    salary: 0,
    status: 'Aberta',
    companyId: 0,
    skillsId: [],
  });

  const [companies, setCompanies] = useState<Company[]>([]);
  const [skills, setSkills] = useState<Skill[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadDependencies();
    if (id) {
      loadJob(Number(id));
    }
  }, [id]);

  const loadDependencies = async () => {
    try {
      const [companiesData, skillsData] = await Promise.all([
        getCompanies(),
        getSkills(),
      ]);
      setCompanies(companiesData);
      setSkills(skillsData);
    } catch (err) {
      console.error('Erro ao carregar dependências', err);
    }
  };

  const loadJob = async (jobId: number) => {
    try {
      setLoading(true);
      const data = await getJobById(jobId);
      setJob({
        title: data.title,
        description: data.description,
        type: data.type,
        salary: data.salary,
        status: data.status,
        companyId: data.company.id || 0,
        skillsId: data.requiredSkills.map(s => s.id!),
      });
    } catch (err) {
      setError('Erro ao carregar vaga.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    setJob((prev) => ({ ...prev, [name]: value }));
  };

  const handleSkillsChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedOptions = Array.from(e.target.selectedOptions, (option) => Number(option.value));
    setJob((prev) => ({ ...prev, skillsId: selectedOptions }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      if (id) {
        await updateJob(Number(id), {
          ...job,
          status: jobStatuses[job.status as keyof typeof jobStatuses],
        });
      } else {
        await createJob({
          ...job,
          status: jobStatuses[job.status as keyof typeof jobStatuses],
        });
      }
      navigate('/jobs');
    } catch (err) {
      setError('Erro ao salvar vaga.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  if (loading && id) return <div className="p-4">Carregando...</div>;

  return (
    <div className="container mx-auto p-4 max-w-2xl">
      <h1 className="text-2xl font-bold mb-6">
        {id ? 'Editar Vaga' : 'Nova Vaga'}
      </h1>

      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
          {error}
        </div>
      )}

      <form onSubmit={handleSubmit} className="bg-white p-6 rounded shadow-md space-y-4">
        <div>
          <label className="block text-gray-700 font-bold mb-2" htmlFor="title">
            Título
          </label>
          <input
            type="text"
            id="title"
            name="title"
            value={job.title}
            onChange={handleChange}
            className="w-full border border-gray-300 px-3 py-2 rounded focus:outline-none focus:border-blue-500"
            required
          />
        </div>

        <div>
          <label className="block text-gray-700 font-bold mb-2" htmlFor="companyId">
            Empresa
          </label>
          <select
            id="companyId"
            name="companyId"
            value={job.companyId}
            onChange={handleChange}
            className="w-full border border-gray-300 px-3 py-2 rounded focus:outline-none focus:border-blue-500"
            required
            disabled={!!id}
          >
            <option value="">Selecione uma empresa</option>
            {companies.map((company) => (
              <option key={company.id} value={company.id}>
                {company.corporateName} ({company.cnpj})
              </option>
            ))}
          </select>
        </div>

        <div>
          <label className="block text-gray-700 font-bold mb-2" htmlFor="description">
            Descrição
          </label>
          <textarea
            id="description"
            name="description"
            value={job.description}
            onChange={handleChange}
            className="w-full border border-gray-300 px-3 py-2 rounded focus:outline-none focus:border-blue-500"
            rows={4}
          />
        </div>

        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-gray-700 font-bold mb-2" htmlFor="type">
              Tipo (CLT, PJ, Estágio...)
            </label>
            <input
              type="text"
              id="type"
              name="type"
              value={job.type}
              onChange={handleChange}
              className="w-full border border-gray-300 px-3 py-2 rounded focus:outline-none focus:border-blue-500"
            />
          </div>

          <div>
            <label className="block text-gray-700 font-bold mb-2" htmlFor="salary">
              Salário
            </label>
            <input
              type="number"
              id="salary"
              name="salary"
              value={job.salary}
              onChange={handleChange}
              className="w-full border border-gray-300 px-3 py-2 rounded focus:outline-none focus:border-blue-500"
            />
          </div>
        </div>

        <div>
          <label className="block text-gray-700 font-bold mb-2" htmlFor="status">
            Status
          </label>
          <select
            id="status"
            name="status"
            value={jobStatusesReverse[job.status as keyof typeof jobStatusesReverse]}
            onChange={handleChange}
            className="w-full border border-gray-300 px-3 py-2 rounded focus:outline-none focus:border-blue-500"
          >
            <option value="Aberta">Aberta</option>
            <option value="Fechada">Fechada</option>
            <option value="Em análise">Em análise</option>
          </select>
        </div>

        <div>
          <label className="block text-gray-700 font-bold mb-2" htmlFor="skillsId">
            Habilidades Requeridas (Segure Ctrl para selecionar múltiplas)
          </label>
          <select
            id="skillsId"
            name="skillsId"
            multiple
            value={job.skillsId.map(String)}
            onChange={handleSkillsChange}
            className="w-full border border-gray-300 px-3 py-2 rounded focus:outline-none focus:border-blue-500 h-32"
          >
            {skills.map((skill) => (
              <option key={skill.id} value={skill.id}>
                {skill.name}
              </option>
            ))}
          </select>
        </div>

        <div className="flex justify-end space-x-2 pt-4">
          <button
            type="button"
            onClick={() => navigate('/jobs')}
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

export default JobForm;
