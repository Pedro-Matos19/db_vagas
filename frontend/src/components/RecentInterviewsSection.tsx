import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getInterviewSchedule } from '../services/interviewService';
import type { InterviewScheduleDetail } from '../types';

function formatInterviewDateTime(isoString: string): string {
  try {
    const date = new Date(isoString);
    return date.toLocaleDateString('pt-BR', {
      weekday: 'short',
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

function InterviewStatusBadge({ status }: { status: string }) {
  const statusLower = status.toLowerCase();
  const isScheduled = statusLower.includes('agendad') || statusLower.includes('agendada');
  const isDone = statusLower.includes('realizada') || statusLower.includes('concluíd');
  const isCancelled = statusLower.includes('cancelad');

  const bgClass = isDone
    ? 'bg-green-100 text-green-800'
    : isCancelled
      ? 'bg-red-100 text-red-800'
      : isScheduled
        ? 'bg-blue-100 text-blue-800'
        : 'bg-gray-100 text-gray-800';

  return (
    <span
      className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${bgClass}`}
    >
      {status}
    </span>
  );
}

function RecentInterviewsTable({ items }: { items: InterviewScheduleDetail[] }) {
  return (
    <div className="overflow-x-auto">
      <table className="min-w-full divide-y divide-gray-200">
        <thead className="bg-gray-50">
          <tr>
            <th
              scope="col"
              className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider"
            >
              Data / Horário
            </th>
            <th
              scope="col"
              className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider"
            >
              Candidato
            </th>
            <th
              scope="col"
              className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider hidden sm:table-cell"
            >
              Vaga
            </th>
            <th
              scope="col"
              className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider hidden md:table-cell"
            >
              Empresa
            </th>
            <th
              scope="col"
              className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider"
            >
              Local / Link
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
              key={item.idInterview}
              className="hover:bg-gray-50 transition-colors"
            >
              <td className="px-4 py-3 text-sm text-gray-700 whitespace-nowrap">
                {formatInterviewDateTime(item.interviewDateTime)}
              </td>
              <td className="px-4 py-3 text-sm font-medium text-gray-900">
                {item.candidateName}
              </td>
              <td className="px-4 py-3 text-sm text-gray-600 hidden sm:table-cell">
                {item.jobTitle}
              </td>
              <td className="px-4 py-3 text-sm text-gray-600 hidden md:table-cell">
                {item.companyName}
              </td>
              <td className="px-4 py-3 text-sm text-gray-600 max-w-[180px] truncate" title={item.meetingLink}>
                {item.meetingLink}
              </td>
              <td className="px-4 py-3">
                <InterviewStatusBadge status={item.interviewStatus} />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

function RecentInterviewsCards({ items }: { items: InterviewScheduleDetail[] }) {
  return (
    <div className="space-y-3 sm:hidden">
      {items.map((item) => (
        <div
          key={item.idInterview}
          className="bg-gray-50 rounded-lg p-4 border border-gray-200"
        >
          <div className="flex justify-between items-start gap-2 mb-2">
            <span className="text-sm font-semibold text-gray-900">
              {formatInterviewDateTime(item.interviewDateTime)}
            </span>
            <InterviewStatusBadge status={item.interviewStatus} />
          </div>
          <p className="text-sm font-medium text-gray-800 mb-1">
            {item.candidateName}
          </p>
          <p className="text-sm text-gray-700 mb-1">
            <span className="text-gray-500">Vaga:</span> {item.jobTitle}
          </p>
          <p className="text-sm text-gray-600 mb-1">
            <span className="text-gray-500">Empresa:</span> {item.companyName}
          </p>
          <p className="text-xs text-gray-500 truncate" title={item.meetingLink}>
            {item.meetingLink}
          </p>
        </div>
      ))}
    </div>
  );
}

const RecentInterviewsSection: React.FC = () => {
  const [items, setItems] = useState<InterviewScheduleDetail[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    let cancelled = false;
    const load = async () => {
      try {
        setLoading(true);
        setError(null);
        const data = await getInterviewSchedule();
        if (!cancelled) setItems(data ?? []);
      } catch (err) {
        if (!cancelled) {
          setError('Erro ao carregar as últimas entrevistas.');
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
            Últimas Entrevistas Agendadas
          </h2>
          <Link
            to="/applications"
            className="text-sm font-medium text-blue-600 hover:text-blue-800 transition-colors"
          >
            Ver candidaturas →
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
            Nenhuma entrevista agendada.
          </div>
        )}
        {!loading && !error && items.length > 0 && (
          <>
            <RecentInterviewsCards items={items} />
            <div className="hidden sm:block">
              <RecentInterviewsTable items={items} />
            </div>
          </>
        )}
      </div>
    </section>
  );
};

export default RecentInterviewsSection;
