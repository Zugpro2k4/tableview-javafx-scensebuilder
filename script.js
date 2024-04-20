// Lấy tham chiếu đến form
const form = document.querySelector('.contact-form');

// Thêm sự kiện submit cho form
form.addEventListener('submit', function(event) {
  event.preventDefault(); // Ngăn chặn form submit mặc định

  // Lấy các trường nhập liệu
  const nameInput = document.getElementById('name');
  const emailInput = document.getElementById('email');
  const phoneInput = document.getElementById('phone');
  const messageInput = document.getElementById('message');

  // Kiểm tra các trường nhập liệu
  let isValid = true;

  if (!nameInput.value.trim()) {
    showError(nameInput, 'Trường này bắt buộc');
    isValid = false;
  } else {
    clearError(nameInput);
  }

  if (!emailInput.value.trim()) {
    showError(emailInput, 'Trường này bắt buộc');
    isValid = false;
  } else {
    clearError(emailInput);
  }

  if (!phoneInput.value.trim()) {
    showError(phoneInput, 'Trường này bắt buộc');
    isValid = false;
  } else {
    clearError(phoneInput);
  }

  if (!messageInput.value.trim()) {
    showError(messageInput, 'Trường này bắt buộc');
    isValid = false;
  } else {
    clearError(messageInput);
  }

  // Nếu tất cả các trường hợp đều hợp lệ, cho phép form submit
  if (isValid) {
    form.submit();
  }
});

// Hàm hiển thị lỗi
function showError(input, message) {
  const formGroup = input.closest('.form-group');
  formGroup.classList.add('has-error');

  const errorMessage = formGroup.querySelector('.error-message');
  if (!errorMessage) {
    const newErrorMessage = document.createElement('div');
    newErrorMessage.classList.add('error-message');
    newErrorMessage.textContent = message;
    formGroup.appendChild(newErrorMessage);
  } else {
    errorMessage.textContent = message;
  }
}

// Hàm xóa lỗi
function clearError(input) {
  const formGroup = input.closest('.form-group');
  formGroup.classList.remove('has-error');

  const errorMessage = formGroup.querySelector('.error-message');
  if (errorMessage) {
    errorMessage.remove();
  }
}