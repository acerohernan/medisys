const sidebar = document.getElementById("sidebar");
const overlay = document.getElementById("overlay");
const mainContent = document.getElementById("mainContent");
const mobileBtn = document.getElementById("mobileToggle");

/* ── Mobile open/close ── */
function openMobile() {
  sidebar.classList.add("mobile-open");
  overlay.classList.add("show");
  document.body.style.overflow = "hidden";
}
function closeMobile() {
  sidebar.classList.remove("mobile-open");
  overlay.classList.remove("show");
  document.body.style.overflow = "";
}

mobileBtn?.addEventListener("click", openMobile);
overlay.addEventListener("click", closeMobile);

/* ── Resize guard ── */
window.addEventListener("resize", () => {
  if (window.innerWidth >= 768) closeMobile();
});
