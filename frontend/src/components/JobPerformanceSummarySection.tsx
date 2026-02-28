import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getJobPerformanceSummary } from '../services/jobService';
import type { JobPerformanceSummaryItem } from '../types';

function formatSalary(value: number): string {
  return new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL',
  }).format(value);
}

function JobStatusBadge({ status }: { status: string }) {
  const statusLower = status.toLowerCase();
  const isOpen = statusLower.includes('abert') || statusLower.includes('aberta');
  const isClosed = statusLower.includes('fechad');

  const bgClass = isOpen
    ? 'bg-green-100 text-green-800'
    : isClosed
      ? 'bg-gray-100 text-gray-700'
      : 'bg-amber-100 text-amber-800';

  return (
    <span
      className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${bgClass}`}
    >
      {status}
    </span>
  );
}

function PerformanceSummaryTable({ items }: { items: JobPerformanceSummaryItem[] }) {
  return (
    <div className="overflow-x-auto">
      <table className="min-w-full divide-y divide-gray-200">
        <thead className="bg-gray-50">
          <tr>
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
              className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider"
            >
              Status
            </th>
            <th
              scope="col"
              className="px-4 py-3 text-right text-xs font-semibold text-gray-600 uppercase tracking-wider"
            >
              Salário
            </th>
            <th
              scope="col"
              className="px-4 py-3 text-center text-xs font-semibold text-gray-600 uppercase tracking-wider"
            >
              Candidatos
            </th>
          </tr>
        </thead>
        <tbody className="bg-white divide-y divide-gray-200">
          {items.map((item, index) => (
            <tr
              key={`${item.companyName}-${item.jobTitle}-${index}`}
              className="hover:bg-gray-50 transition-colors"
            >
              <td className="px-4 py-3 text-sm font-medium text-gray-900">
                {item.jobTitle}
              </td>
              <td className="px-4 py-3 text-sm text-gray-600 hidden sm:table-cell">
                {item.companyName}
              </td>
              <td className="px-4 py-3">
                <JobStatusBadge status={item.jobStatus} />
              </td>
              <td className="px-4 py-3 text-sm text-gray-700 text-right whitespace-nowrap">
                {formatSalary(item.salary)}
              </td>
              <td className="px-4 py-3 text-sm text-gray-700 text-center">
                <span
                  className={`inline-flex items-center justify-center min-w-8 px-2 py-0.5 rounded-full text-xs font-medium ${
                    item.totalCandidates > 0 ? 'bg-blue-100 text-blue-800' : 'bg-gray-100 text-gray-600'
                  }`}
                >
                  {item.totalCandidates}
                </span>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

function PerformanceSummaryCards({ items }: { items: JobPerformanceSummaryItem[] }) {
  return (
    <div className="space-y-3 sm:hidden">
      {items.map((item, index) => (
        <div
          key={`${item.companyName}-${item.jobTitle}-${index}`}
          className="bg-gray-50 rounded-lg p-4 border border-gray-200"
        >
          <div className="flex justify-between items-start gap-2 mb-2">
            <span className="text-sm font-semibold text-gray-900">
              {item.jobTitle}
            </span>
            <JobStatusBadge status={item.jobStatus} />
          </div>
          <p className="text-sm text-gray-600 mb-1">
            <span className="text-gray-500">Empresa:</span> {item.companyName}
          </p>
          <div className="flex flex-wrap items-center justify-between gap-2 mt-2 pt-2 border-t border-gray-200">
            <span className="text-sm font-medium text-gray-800">
              {formatSalary(item.salary)}
            </span>
            <span
              className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${
                item.totalCandidates > 0 ? 'bg-blue-100 text-blue-800' : 'bg-gray-100 text-gray-600'
              }`}
            >
              {item.totalCandidates} candidato{item.totalCandidates !== 1 ? 's' : ''}
            </span>
          </div>
        </div>
      ))}
    </div>
  );
}

const JobPerformanceSummarySection: React.FC = () => {
  const [items, setItems] = useState<JobPerformanceSummaryItem[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    let cancelled = false;
    const load = async () => {
      try {
        setLoading(true);
        setError(null);
        const data = await getJobPerformanceSummary();
        if (!cancelled) setItems(data ?? []);
      } catch (err) {
        if (!cancelled) {
          setError('Erro ao carregar o resumo de vagas.');
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
            Resumo de Vagas (Performance)
          </h2>
          <Link
            to="/jobs"
            className="text-sm font-medium text-blue-600 hover:text-blue-800 transition-colors"
          >
            Ver todas as vagas →
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
            Nenhuma vaga encontrada.
          </div>
        )}
        {!loading && !error && items.length > 0 && (
          <>
            <PerformanceSummaryCards items={items} />
            <div className="hidden sm:block">
              <PerformanceSummaryTable items={items} />
            </div>
          </>
        )}
      </div>
    </section>
  );
};

export default JobPerformanceSummarySection;
