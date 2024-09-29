 function mascaraTelefone(event) {
    let telefone = event.target.value.replace(/\D/g, '');
    telefone = telefone.replace(/^(\d{2})(\d)/g, '$1 $2');
    telefone = telefone.replace(/(\d{1})(\d{4})(\d{4})$/, '$1 $2-$3');
    event.target.value = telefone;
  }

  document.getElementById('telefone').addEventListener('input', mascaraTelefone);

  window.addEventListener('load', function() {
    let telefoneInput = document.getElementById('telefone');
    mascaraTelefone({ target: telefoneInput });
  });

