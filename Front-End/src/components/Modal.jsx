import React from "react";

const Modal = ({ isOpen, onClose, children }) => {
  if (!isOpen) return null;

  return (
    <div className='fixed inset-0 bg-black bg-opacity-40 flex items-center justify-center z-50'>
      <div className='bg-white rounded-lg shadow-lg p-6 w-full max-w-md relative'>
        <button
          onClick={onClose}
          className='absolute top-2 right-3 text-gray-600 text-xl'
        >
          &times;
        </button>
        {children}
      </div>
    </div>
  );
};

export default Modal;
