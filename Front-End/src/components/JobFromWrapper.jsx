import React, { useState } from "react";
import JobForm from "./JobFrom"; // your existing form
import Modal from "./Modal";

const JobFormWrapper = () => {
  const [open, setOpen] = useState(false);

  const handleFormSubmit = (data) => {
    console.log(data); // send to backend
    setOpen(false);
  };

  return (
    <>
      <button
        onClick={() => setOpen(true)}
        className='bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700'
      >
        Add New Job
      </button>

      <Modal isOpen={open} onClose={() => setOpen(false)}>
        <JobForm onSubmit={handleFormSubmit} />
      </Modal>
    </>
  );
};

export default JobFormWrapper;
