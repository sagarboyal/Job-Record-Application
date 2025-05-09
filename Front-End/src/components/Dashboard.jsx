import React, { useState } from "react";
import JobForm from "./JobFrom"; // make sure the path is correct

const Dashboard = () => {
  const [showForm, setShowForm] = useState(false);
  const [jobs, setJobs] = useState([
    {
      company: "Google",
      role: "SDE",
      status: "INTERVIEW",
      appliedAt: "2025-05-01",
      url: "https://careers.google.com",
    },
  ]);

  const handleAddJob = (newJob) => {
    setJobs([...jobs, newJob]);
    setShowForm(false);
  };

  return (
    <div className='p-6 bg-gray-100 min-h-screen'>
      {/* Header */}
      <div className='flex justify-between items-center mb-4'>
        <h1 className='text-2xl font-bold'>Job Dashboard</h1>
        <button
          onClick={() => setShowForm(true)}
          className='bg-blue-500 text-white px-4 py-2 rounded'
        >
          Add New Job
        </button>
      </div>

      {/* Summary Cards */}
      <div className='grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 mb-6'>
        <div className='bg-white p-4 rounded shadow text-center'>
          <h2 className='text-lg font-semibold'>Total Jobs</h2>
          <p className='text-2xl text-blue-600'>{jobs.length}</p>
        </div>
        {/* Add more summary cards here */}
      </div>

      {/* Filter + Export Buttons */}
      <div className='flex flex-wrap justify-between items-center mb-4 gap-4'>
        <div>
          <input
            type='text'
            placeholder='Search Company...'
            className='p-2 border rounded mr-2'
          />
        </div>
        <div>
          <button className='bg-green-600 text-white px-4 py-2 rounded mr-2'>
            Export PDF
          </button>
          <button className='bg-yellow-500 text-white px-4 py-2 rounded mr-2'>
            Export CSV
          </button>
          <button className='bg-purple-600 text-white px-4 py-2 rounded'>
            Export DOCX
          </button>
        </div>
      </div>

      {/* Job Table */}
      <div className='bg-white rounded shadow p-4'>
        <table className='min-w-full'>
          <thead>
            <tr className='text-left border-b'>
              <th className='p-2'>Company</th>
              <th className='p-2'>Role</th>
              <th className='p-2'>Status</th>
              <th className='p-2'>Applied At</th>
              <th className='p-2'>Actions</th>
            </tr>
          </thead>
          <tbody>
            {jobs.map((job, index) => (
              <tr key={index} className='border-b'>
                <td className='p-2'>{job.company}</td>
                <td className='p-2'>{job.role}</td>
                <td className='p-2'>{job.status}</td>
                <td className='p-2'>{job.appliedAt}</td>
                <td className='p-2'>
                  <a
                    href={job.url}
                    className='text-blue-500 underline mr-2'
                    target='_blank'
                    rel='noopener noreferrer'
                  >
                    View
                  </a>
                  Edit | Delete
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Modal */}
      {showForm && (
        <div className='fixed inset-0 bg-black/30 flex justify-center items-center z-50'>
          <div className='bg-white rounded p-6 w-full max-w-lg relative z-50'>
            <button
              onClick={() => setShowForm(false)}
              className='absolute top-2 right-3 text-xl text-gray-500'
            >
              &times;
            </button>
            <JobForm onSubmit={handleAddJob} />
          </div>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
