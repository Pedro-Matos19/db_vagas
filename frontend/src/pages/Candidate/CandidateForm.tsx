import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import {
  createCandidate,
  getCandidateById,
  updateCandidate,
} from '../../services/candidateService';
import { type Candidate, type CandidateRequest } from '../../types';

const CandidateForm: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [createData, setCreateData] = useState<CandidateRequest>({
    cpf: '',
    name: '',
    birthDate: '',
    userId: 0,
    skillsId: [],
    coursesId: [],
  });

  const [updateData, setUpdateData] = useState<Candidate>({
    cpf: '',
    name: '',
    birthDate: '',
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (id) {
      loadCandidate(Number(id));
    }
  }, [id]);

  const loadCandidate = async (candidateId: number) => {
    try {
      setLoading(true);
      const data = await getCandidateById(candidateId);
      setUpdateData(data);
    } catch (err) {
      setError('Erro ao carregar dados do candidato.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    if (id) {
      setUpdateData((prev) => ({ ...prev, [name]: value }));
    } else {
      setCreateData((prev) => ({ ...prev, [name]: value }));
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      if (id) {
        await updateCandidate(Number(id), updateData);
      } else {
        // Para simplificar, estamos hardcoding userId = 1 se não fornecido
        // Em um app real, você selecionaria um usuário existente ou criaria um
        const dataToSend = { ...createData, userId: createData.userId || 1 };
        await createCandidate(dataToSend);
      }
      navigate('/candidates');
    } catch (err) {
      setError('Erro ao salvar candidato.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  if (loading && id) return <div className="p-4">Carregando...</div>;

  return (
    <div className="container mx-auto p-4 max-w-2xl">
      <h1 className="text-2xl font-bold mb-6">
        {id ? 'Editar Candidato' : 'Novo Candidato'}
      </h1>

      {error && (
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
          {error}
        </div>
      )}

      <form
        onSubmit={handleSubmit}
        className="bg-white p-6 rounded shadow-md space-y-4"
      >
        <div>
          <label className="block text-gray-700 font-bold mb-2">
            Nome Completo
          </label>
          <input
            type="text"
            name="name"
            value={id ? updateData.name : createData.name}
            onChange={handleChange}
            className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            required
          />
        </div>

        <div>
          <label className="block text-gray-700 font-bold mb-2">CPF</label>
          <input
            type="text"
            name="cpf"
            value={id ? updateData.cpf : createData.cpf}
            onChange={handleChange}
            className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            required
          />
        </div>

        <div>
          <label className="block text-gray-700 font-bold mb-2">
            Data de Nascimento
          </label>
          <input
            type="date"
            name="birthDate"
            value={id ? updateData.birthDate : createData.birthDate}
            onChange={handleChange}
            className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            required
          />
        </div>

        {!id && (
          <div>
            <label className="block text-gray-700 font-bold mb-2">
              ID do Usuário (Temporário)
            </label>
            <input
              type="number"
              name="userId"
              value={createData.userId}
              onChange={handleChange}
              className="w-full border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Digite o ID de um usuário existente"
            />
            <p className="text-xs text-gray-500 mt-1">
              * Em produção, isso seria um select ou criado automaticamente.
            </p>
          </div>
        )}

        <div className="flex justify-end space-x-4 mt-6">
          <button
            type="button"
            onClick={() => navigate('/candidates')}
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

export default CandidateForm;
