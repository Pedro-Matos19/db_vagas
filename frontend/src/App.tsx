import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Layout from './components/Layout';
import ApplicationList from './pages/Application/ApplicationList';
import ApplicationForm from './pages/Application/ApplicationForm';
import CandidateForm from './pages/Candidate/CandidateForm';
import CandidateList from './pages/Candidate/CandidateList';
import CompanyForm from './pages/Company/CompanyForm';
import CompanyList from './pages/Company/CompanyList';
import CourseList from './pages/Course/CourseList';
import CourseForm from './pages/Course/CourseForm';
import Home from './pages/home/Home';
import JobList from './pages/Job/JobList';
import JobForm from './pages/Job/JobForm';
import SkillList from './pages/Skill/SkillList';
import SkillForm from './pages/Skill/SkillForm';

function App() {
  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/candidates" element={<CandidateList />} />
          <Route path="/candidates/new" element={<CandidateForm />} />
          <Route path="/candidates/:id" element={<CandidateForm />} />
          <Route path="/companies" element={<CompanyList />} />
          <Route path="/companies/new" element={<CompanyForm />} />
          <Route path="/companies/:id" element={<CompanyForm />} />
          <Route path="/jobs" element={<JobList />} />
          <Route path="/jobs/new" element={<JobForm />} />
          <Route path="/jobs/:id" element={<JobForm />} />
          <Route path="/courses" element={<CourseList />} />
          <Route path="/courses/new" element={<CourseForm />} />
          <Route path="/courses/:id" element={<CourseForm />} />
          <Route path="/skills" element={<SkillList />} />
          <Route path="/skills/new" element={<SkillForm />} />
          <Route path="/skills/:id" element={<SkillForm />} />
          <Route path="/applications" element={<ApplicationList />} />
          <Route path="/applications/new" element={<ApplicationForm />} />
          <Route path="/applications/:id" element={<ApplicationForm />} />
        </Routes>
      </Layout>
    </BrowserRouter>
  );
}

export default App;
