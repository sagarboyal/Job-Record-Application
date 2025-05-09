import React, { useState, useEffect } from "react";
import { apiCall } from "../api/api";
import axios from "axios";

const JobForm = ({ onSubmit }) => {
  const [form, setForm] = useState({
    company: "",
    source: "",
    status: "",
    roleName: "",
    appliedAt: "",
    url: "",
    notes: "",
  });

  const [jobRoles, setJobRoles] = useState([]);
  const [showCustomRole, setShowCustomRole] = useState(false);
  useEffect(() => {
    // Function to fetch job roles from the API
    const fetchJobRoles = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/api/jobs/default"
        ); // Ensure the correct API URL
        setJobRoles(response.data); // Set the job roles to state
      } catch (error) {
        console.error("Error fetching job roles:", error);
      } finally {
        setLoading(false); // Set loading to false after the data is fetched
      }
    };
    fetchJobRoles();
  }, []); // Empty dependency array ensures the fetch happens only once on mount

  const [loading, setLoading] = useState(false);

  if (loading) {
    return <div>Loading...</div>; // Display a loading message or spinner while fetching data
  }

  const [error, setError] = useState(null);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      const result = await apiCall("/jobs", "POST", form); // Call API using the centralized method
      onSubmit(result); // Pass the result back to parent component
      alert("Job added successfully!");
    } catch (err) {
      setError(err.message || "An error occurred while submitting the job.");
    } finally {
      setLoading(false);
    }
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
        name='source'
        placeholder='Source Name'
        value={form.source}
        onChange={handleChange}
        className='w-full p-2 mb-3 border border-gray-300 rounded'
        required
      />

      <select
        name='role'
        value={form.role}
        onChange={(e) => {
          handleChange(e);
          setShowCustomRole(e.target.value === "OTHER");
        }}
        className='w-full p-2 mb-3 border border-gray-300 rounded'
      >
        <option value=''>Select Role</option>
        {jobRoles && Array.isArray(jobRoles) && jobRoles.length > 0 ? (
          jobRoles.map((role) => (
            <option key={role.id} value={role.name}>
              {role.name}
            </option>
          ))
        ) : (
          <option>No roles available</option>
        )}
        <option value='OTHER'>Other</option>
      </select>

      {showCustomRole && (
        <input
          type='text'
          name='role'
          placeholder='Enter custom role'
          value={form.role}
          onChange={handleChange}
          className='w-full p-2 mb-3 border border-gray-300 rounded'
          required
        />
      )}

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

      <textarea
        name='notes'
        placeholder='Notes'
        value={form.notes}
        onChange={handleChange}
        className='w-full p-2 mb-3 border border-gray-300 rounded'
        rows='4'
      ></textarea>

      {error && <p className='text-red-500 mb-4'>{error}</p>}

      <button
        type='submit'
        className={`bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 ${
          loading ? "opacity-50 cursor-not-allowed" : ""
        }`}
        disabled={loading}
      >
        {loading ? "Submitting..." : "Submit"}
      </button>
    </form>
  );
};

export default JobForm;
