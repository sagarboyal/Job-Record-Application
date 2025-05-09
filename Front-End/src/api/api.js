import axios from 'axios';

export const BASE_URL = "http://localhost:8080"; // Your backend base URL

// Utility function to make API calls
export const apiCall = async (endpoint, method = "GET", body = null) => {
  const url = `${BASE_URL}${endpoint}`;
  
  const options = {
    method,
    headers: {
      "Content-Type": "application/json",
    },
  };

  // Add the body if there is one (for POST, PUT requests)
  if (body) {
    options.data = body;
  }

  try {
    const response = await axios(url, options);
    
    // Axios already throws an error for status codes outside 2xx,
    // so we can directly return the response
    return response.data;
  } catch (error) {
    // Axios error handling
    const errorMessage = error.response?.data?.message || error.message || "Something went wrong!";
    throw new Error(errorMessage);
  }
};
