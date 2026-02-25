import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { deleteCompany, getCompanies } from '../../services/companyService';
import { type Company } from '../../types';

const CompanyList: React.FC = () => {
  const [companies, setCompanies] = useState<Company[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadCompanies();
  }, []);

  const loadCompanies = async () => {
    try {
      setLoading(true);
      const data = await getCompanies();
      setCompanies(data);
    } catch (err) {
      setError('Erro ao carregar empresas.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Tem certeza que deseja excluir esta empresa?')) {
      try {
        await deleteCompany(id);
        setCompanies(companies.filter((c) => c.id !== id));
      } catch (err) {
        alert('Erro ao excluir empresa.');
        console.error(err);
      }
    }
  };

  if (loading) return <div className="p-4">Carregando...</div>;
  if (error) return <div className="p-4 text-red-500">{error}</div>;

  return (
    <div className="container mx-auto p-4">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Empresas</h1>
        <Link
          to="/companies/new"
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Nova Empresa
        </Link>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {companies.map((company) => (
          <div
            key={company.id}
            className="bg-white p-6 rounded shadow-md border border-gray-200"
          >
            <h2 className="text-xl font-bold mb-2">{company.corporateName}</h2>
            <p className="text-gray-600 mb-2">
              <strong>CNPJ:</strong> {company.cnpj}
            </p>
            <p className="text-gray-600 mb-2">
              <strong>Setor:</strong> {company.sector}
            </p>
            <p className="text-gray-600 mb-2">
              <strong>Localização:</strong> {company.location}
            </p>
            {company.website && (
              <a
                href={company.website}
                target="_blank"
                rel="noopener noreferrer"
                className="text-blue-500 hover:underline block mb-4"
              >
                Visitar site
              </a>
            )}
            <div className="flex justify-end space-x-2 mt-4">
              <Link
                to={`/companies/${company.id}`}
                className="text-yellow-600 hover:text-yellow-800"
              >
                Editar
              </Link>
              <button
                onClick={() => handleDelete(company.id!)}
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

export default CompanyList;
