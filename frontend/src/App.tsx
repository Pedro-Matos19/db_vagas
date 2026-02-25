import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import ApplicationList from './pages/Application/ApplicationList';
import CandidateForm from './pages/Candidate/CandidateForm';
import CandidateList from './pages/Candidate/CandidateList';
import CompanyForm from './pages/Company/CompanyForm';
import CompanyList from './pages/Company/CompanyList';
import CourseList from './pages/Course/CourseList';
import Home from './pages/home/Home';
import JobList from './pages/Job/JobList';
import SkillList from './pages/Skill/SkillList';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/candidates" element={<CandidateList />} />
        <Route path="/candidates/new" element={<CandidateForm />} />
        <Route path="/candidates/:id" element={<CandidateForm />} />
        <Route path="/companies" element={<CompanyList />} />
        <Route path="/companies/new" element={<CompanyForm />} />
        <Route path="/companies/:id" element={<CompanyForm />} />
        <Route path="/jobs" element={<JobList />} />
        <Route path="/courses" element={<CourseList />} />
        <Route path="/skills" element={<SkillList />} />
        <Route path="/applications" element={<ApplicationList />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
