// ── DEMO TOGGLE ──
function setView(mode) {
  const conCitas = document.getElementById("view-con-citas");
  const sinCitas = document.getElementById("view-empty");
  const btnCon = document.getElementById("btn-con-citas");
  const btnSin = document.getElementById("btn-sin-citas");

  // Nav active links
  const navDash = document.getElementById("nav-dashboard");
  const navCitas = document.querySelector(".nav-link-citas");

  if (mode === "con-citas") {
    conCitas.style.display = "";
    sinCitas.style.display = "none";
    btnCon.classList.add("active");
    btnSin.classList.remove("active");
    navDash.classList.remove("active");
    navCitas.classList.add("active");
  } else {
    conCitas.style.display = "none";
    sinCitas.style.display = "";
    btnCon.classList.remove("active");
    btnSin.classList.add("active");
    navDash.classList.add("active");
    navCitas.classList.remove("active");
  }
}

// Init
setView("con-citas");

// ── BOTONES (demo feedback) ──
document
  .querySelectorAll(
    ".btn-gestionar, .btn-ver, .btn-nueva-cita, .btn-primera-cita",
  )
  .forEach((btn) => {
    btn.addEventListener("click", function (e) {
      e.preventDefault();
      const original = this.innerHTML;
      this.innerHTML = '<i class="bi bi-check-lg me-1"></i> ¡Listo!';
      setTimeout(() => {
        this.innerHTML = original;
      }, 1200);
    });
  });

document.querySelectorAll(".btn-store").forEach((btn) => {
  btn.addEventListener("click", function () {
    const original = this.innerHTML;
    this.innerHTML = '<i class="bi bi-check-lg"></i> Redirigiendo…';
    setTimeout(() => {
      this.innerHTML = original;
    }, 1200);
  });
});
