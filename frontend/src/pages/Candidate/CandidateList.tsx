import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import {
  deleteCandidate,
  getCandidates,
} from '../../services/candidateService';
import { type Candidate } from '../../types';

const CandidateList: React.FC = () => {
  const [candidates, setCandidates] = useState<Candidate[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadCandidates();
  }, []);

  const loadCandidates = async () => {
    try {
      setLoading(true);
      const data = await getCandidates();
      setCandidates(data);
    } catch (err) {
      setError('Erro ao carregar candidatos.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Tem certeza que deseja excluir este candidato?')) {
      try {
        await deleteCandidate(id);
        setCandidates(candidates.filter((c) => c.id !== id));
      } catch (err) {
        alert('Erro ao excluir candidato.');
        console.error(err);
      }
    }
  };

  if (loading) return <div className="p-4">Carregando...</div>;
  if (error) return <div className="p-4 text-red-500">{error}</div>;

  return (
    <div className="container mx-auto p-4">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Candidatos</h1>
        <Link
          to="/candidates/new"
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Novo Candidato
        </Link>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {candidates.map((candidate) => (
          <div
            key={candidate.id}
            className="bg-white p-6 rounded shadow-md border border-gray-200"
          >
            <h2 className="text-xl font-bold mb-2">{candidate.name}</h2>
            <p className="text-gray-600 mb-2">
              <strong>CPF:</strong> {candidate.cpf}
            </p>
            <p className="text-gray-600 mb-2">
              <strong>Data de Nascimento:</strong> {candidate.birthDate}
            </p>
            <div className="flex justify-end space-x-2 mt-4">
              <Link
                to={`/candidates/${candidate.id}`}
                className="text-yellow-600 hover:text-yellow-800"
              >
                Editar
              </Link>
              <button
                onClick={() => handleDelete(candidate.id!)}
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

export default CandidateList;
