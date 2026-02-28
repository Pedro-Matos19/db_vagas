import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getApplicationDetails } from '../services/applicationService';
import type { ApplicationDetail } from '../types';

function formatApplicationDate(isoString: string): string {
  try {
    const date = new Date(isoString);
    return date.toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  } catch {
    return isoString;
  }
}

function StatusBadge({ status }: { status: string }) {
  const statusLower = status.toLowerCase();
  const isPending = statusLower.includes('análise') || statusLower.includes('pendente');
  const isApproved = statusLower.includes('aprovad') || statusLower.includes('aceit');
  const isRejected = statusLower.includes('rejeit') || statusLower.includes('recusad');

  const bgClass = isApproved
    ? 'bg-green-100 text-green-800'
    : isRejected
      ? 'bg-red-100 text-red-800'
      : isPending
        ? 'bg-amber-100 text-amber-800'
        : 'bg-gray-100 text-gray-800';

  return (
    <span
      className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${bgClass}`}
    >
      {status}
    </span>
  );
}

function RecentApplicationsTable({ items }: { items: ApplicationDetail[] }) {
  return (
    <div className="overflow-x-auto">
      <table className="min-w-full divide-y divide-gray-200">
        <thead className="bg-gray-50">
          <tr>
            <th
              scope="col"
              className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider"
            >
              Candidato
            </th>
            <th
              scope="col"
              className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider"
            >
              Vaga
            </th>
            <th
              scope="col"
              className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider hidden sm:table-cell"
            >
              Empresa
            </th>
            <th
              scope="col"
              className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider hidden md:table-cell"
            >
              Data
            </th>
            <th
              scope="col"
              className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider"
            >
              Status
            </th>
          </tr>
        </thead>
        <tbody className="bg-white divide-y divide-gray-200">
          {items.map((item) => (
            <tr
              key={item.idCandidatura}
              className="hover:bg-gray-50 transition-colors"
            >
              <td className="px-4 py-3 text-sm font-medium text-gray-900 whitespace-nowrap">
                {item.nomeCandidato}
              </td>
              <td className="px-4 py-3 text-sm text-gray-700">
                {item.tituloVaga}
              </td>
              <td className="px-4 py-3 text-sm text-gray-600 hidden sm:table-cell">
                {item.nomeEmpresa}
              </td>
              <td className="px-4 py-3 text-sm text-gray-500 hidden md:table-cell whitespace-nowrap">
                {formatApplicationDate(item.dataAplicacao)}
              </td>
              <td className="px-4 py-3">
                <StatusBadge status={item.statusCandidatura} />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

function RecentApplicationsCards({ items }: { items: ApplicationDetail[] }) {
  return (
    <div className="space-y-3 sm:hidden">
      {items.map((item) => (
        <div
          key={item.idCandidatura}
          className="bg-gray-50 rounded-lg p-4 border border-gray-200"
        >
          <div className="flex justify-between items-start gap-2 mb-2">
            <span className="text-sm font-semibold text-gray-900">
              {item.nomeCandidato}
            </span>
            <StatusBadge status={item.statusCandidatura} />
          </div>
          <p className="text-sm text-gray-700 mb-1">
            <span className="text-gray-500">Vaga:</span> {item.tituloVaga}
          </p>
          <p className="text-sm text-gray-600 mb-1">
            <span className="text-gray-500">Empresa:</span> {item.nomeEmpresa}
          </p>
          <p className="text-xs text-gray-500">
            {formatApplicationDate(item.dataAplicacao)}
          </p>
        </div>
      ))}
    </div>
  );
}

const RecentApplicationsSection: React.FC = () => {
  const [items, setItems] = useState<ApplicationDetail[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    let cancelled = false;
    const load = async () => {
      try {
        setLoading(true);
        setError(null);
        const data = await getApplicationDetails();
        if (!cancelled) setItems(data ?? []);
      } catch (err) {
        if (!cancelled) {
          setError('Erro ao carregar as últimas candidaturas.');
          console.error(err);
        }
      } finally {
        if (!cancelled) setLoading(false);
      }
    };
    load();
    return () => {
      cancelled = true;
    };
  }, []);

  return (
    <section className="bg-white rounded-xl border border-gray-200 shadow-md overflow-hidden">
      <div className="px-5 py-4 border-b border-gray-200 bg-linear-to-r from-gray-50 to-white">
        <div className="flex flex-wrap items-center justify-between gap-3">
          <h2 className="text-xl font-bold text-gray-900">
            Últimas Candidaturas
          </h2>
          <Link
            to="/applications"
            className="text-sm font-medium text-blue-600 hover:text-blue-800 transition-colors"
          >
            Ver todas →
          </Link>
        </div>
      </div>
      <div className="p-4 sm:p-5">
        {loading && (
          <div className="flex items-center justify-center py-12 text-gray-500">
            <span>Carregando...</span>
          </div>
        )}
        {error && (
          <div className="py-8 text-center text-red-600 text-sm">
            {error}
          </div>
        )}
        {!loading && !error && items.length === 0 && (
          <div className="py-8 text-center text-gray-500 text-sm">
            Nenhuma candidatura recente.
          </div>
        )}
        {!loading && !error && items.length > 0 && (
          <>
            <RecentApplicationsCards items={items} />
            <div className="hidden sm:block">
              <RecentApplicationsTable items={items} />
            </div>
          </>
        )}
      </div>
    </section>
  );
};

export default RecentApplicationsSection;
