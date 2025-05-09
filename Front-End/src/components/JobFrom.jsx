import React, { useState, useEffect } from "react";
import { apiCall, BASE_URL } from "../api/api";
import axios from "axios";
import toast from "react-hot-toast";

const JobForm = ({ onSubmit }) => {
  const [form, setForm] = useState({
    company: "",
    source: "",
    status: "",
    roleName: "",
    url: "",
    notes: "",
  });

  const [jobRoles, setJobRoles] = useState([]);
  const [showCustomRole, setShowCustomRole] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Function to fetch job roles from the API
    const fetchJobRoles = async () => {
      try {
        const response = await axios.get(BASE_URL + "/api/jobs/roles/default"); // Ensure the correct API URL
        setJobRoles(response.data); // Set the job roles to state
      } catch (error) {
        console.error("Error fetching job roles:", error);
      } finally {
        setLoading(false); // Set loading to false after fetching
      }
    };
    fetchJobRoles();
  }, []); // Empty dependency array ensures the fetch happens only once on mount

  const [error, setError] = useState(null);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      const result = await apiCall("/api/jobs", "POST", form); // Call API using the centralized method
      onSubmit(result); // Pass the result back to parent component
      toast.success("Job added successfully!");
    } catch (err) {
      toast.error(err.message || "Something went wrong");
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div>Loading...</div>; // Display a loading message or spinner while fetching data
  }

  return (
    <form
      onSubmit={handleSubmit}
      className='max-w-lg mx-auto bg-white p-8 rounded-lg shadow-lg space-y-3' // Reduced space-y
    >
      <h2 className='text-2xl font-semibold text-center text-gray-800 mb-6'>
        Add Job Application
      </h2>

      <div>
        <input
          type='text'
          name='company'
          placeholder='Company Name'
          value={form.company}
          onChange={handleChange}
          className='w-full p-3 mb-4 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition duration-200'
          required
        />
      </div>

      <div>
        <input
          type='text'
          name='source'
          placeholder='Source Name'
          value={form.source}
          onChange={handleChange}
          className='w-full p-3 mb-4 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition duration-200'
          required
        />
      </div>

      <div>
        <select
          name='roleName'
          value={form.roleName}
          onChange={(e) => {
            handleChange(e);
            setShowCustomRole(e.target.value === "OTHER");
          }}
          className='w-full p-3 mb-4 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition duration-200'
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
      </div>

      {showCustomRole && (
        <div>
          <input
            type='text'
            name='role'
            placeholder='Enter custom role'
            value={form.role}
            onChange={handleChange}
            className='w-full p-3 mb-4 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition duration-200'
            required
          />
        </div>
      )}

      <div>
        <select
          name='status'
          value={form.status}
          onChange={handleChange}
          className='w-full p-3 mb-4 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition duration-200'
        >
          <option value=''>Select Status</option>
          <option value='APPLIED'>Applied</option>
          <option value='INTERVIEW'>Interview</option>
          <option value='OFFERED'>Offered</option>
          <option value='REJECTED'>Rejected</option>
          <option value='ACCEPTED'>Accepted</option>
        </select>
      </div>

      <div>
        <input
          type='url'
          name='url'
          placeholder='Job URL'
          value={form.url}
          onChange={handleChange}
          className='w-full p-3 mb-4 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition duration-200'
        />
      </div>

      <div>
        <textarea
          name='notes'
          placeholder='Notes'
          value={form.notes}
          onChange={handleChange}
          className='w-full p-3 mb-4 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500 transition duration-200'
          rows='4'
        ></textarea>
      </div>

      {error && <p className='text-red-500 mb-4 text-center'>{error}</p>}

      <button
        type='submit'
        className={`w-full py-4 text-lg font-semibold text-white rounded-lg shadow-md transition duration-200 focus:outline-none ${
          loading
            ? "bg-gray-500 cursor-not-allowed"
            : "bg-blue-600 hover:bg-blue-700"
        }`}
        disabled={loading}
      >
        {loading ? "Submitting..." : "Submit"}
      </button>
    </form>
  );
};

export default JobForm;
