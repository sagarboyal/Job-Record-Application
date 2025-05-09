import React, { useState } from "react";

const JobForm = ({ onSubmit }) => {
  const [form, setForm] = useState({
    company: "",
    role: "",
    status: "",
    appliedAt: "",
    url: "",
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(form);
  };

  return (
    <form
      onSubmit={handleSubmit}
      className='max-w-md mx-auto bg-white p-6 rounded shadow-md'
    >
      <h2 className='text-xl font-semibold mb-4'>Add Job Application</h2>

      <input
        type='text'
        name='company'
        placeholder='Company Name'
        value={form.company}
        onChange={handleChange}
        className='w-full p-2 mb-3 border border-gray-300 rounded'
        required
      />

      <input
        type='text'
        name='role'
        placeholder='Role'
        value={form.role}
        onChange={handleChange}
        className='w-full p-2 mb-3 border border-gray-300 rounded'
        required
      />

      <select
        name='status'
        value={form.status}
        onChange={handleChange}
        className='w-full p-2 mb-3 border border-gray-300 rounded'
        required
      >
        <option value=''>Select Status</option>
        <option value='APPLIED'>Applied</option>
        <option value='INTERVIEW'>Interview</option>
        <option value='OFFERED'>Offered</option>
        <option value='REJECTED'>Rejected</option>
        <option value='ACCEPTED'>Accepted</option>
      </select>

      <input
        type='date'
        name='appliedAt'
        value={form.appliedAt}
        onChange={handleChange}
        className='w-full p-2 mb-3 border border-gray-300 rounded'
        required
      />

      <input
        type='url'
        name='url'
        placeholder='Job URL'
        value={form.url}
        onChange={handleChange}
        className='w-full p-2 mb-3 border border-gray-300 rounded'
      />

      <button
        type='submit'
        className='bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600'
      >
        Submit
      </button>
    </form>
  );
};

export default JobForm;
