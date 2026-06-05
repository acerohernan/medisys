// ── STATE ──────────────────────────────────────────────────────────────────
const state = {
  specialty: "Medicina General",
  cost: "80.00",
  doctor: "Dr. Roberto Méndez",
  doctorCMP: "52341",
  medicoId: null,
  doctorImg: "https://i.pravatar.cc/80?img=11",
  selectedDate: null,
  selectedTime: null,
  calYear: 2026,
  calMonth: 5,
  icon: "bi bi-shield-plus",
};

// Occupied slots (simulate some booked)
const occupiedSlots = ["10:00 AM", "02:30 PM"];
const morningTimes = [
  "08:00 AM",
  "08:30 AM",
  "09:00 AM",
  "09:30 AM",
  "10:00 AM",
  "10:30 AM",
  "11:00 AM",
  "11:30 AM",
];
const afternoonTimes = [
  "02:00 PM",
  "02:30 PM",
  "03:00 PM",
  "03:30 PM",
  "04:00 PM",
  "04:30 PM",
  "05:00 PM",
  "05:30 PM",
];

// Days with availability (day numbers)
const availDays = [1, 3, 4, 6, 8, 10, 11, 13, 15, 17, 18, 20, 22, 24, 25];

// ── HELPERS ────────────────────────────────────────────────────────────────
function show(id) {
  document.getElementById(id).style.display = "block";
  window.scrollTo({ top: 0, behavior: "smooth" });
}
function hide(id) {
  document.getElementById(id).style.display = "none";
}

const monthNames = [
  "Enero",
  "Febrero",
  "Marzo",
  "Abril",
  "Mayo",
  "Junio",
  "Julio",
  "Agosto",
  "Septiembre",
  "Octubre",
  "Noviembre",
  "Diciembre",
];
const dayNames = ["DOM", "LUN", "MAR", "MIÉ", "JUE", "VIE", "SÁB"];

// ── STEP 1 ─────────────────────────────────────────────────────────────────
function selectSpecialty(el) {
  document
    .querySelectorAll(".specialty-card")
    .forEach((c) => c.classList.remove("selected"));
  el.classList.add("selected");
  state.specialty = el.dataset.specialty;
  state.cost = el.dataset.cost;
  state.icon = el.dataset.icon;
  document.getElementById("sum1-specialty").textContent = state.specialty;
  document.getElementById("sum1-cost").textContent = "S/ " + state.cost;
  document.getElementById("sum1-icon").className = state.icon;
}

function goToStep1() {
  hide("step2");
  hide("step3");
  hide("step4");
  hide("step-success");
  show("step1");
}

function goToStep2() {
  // Update step2 labels
  document.getElementById("step2-specialty-label").textContent =
    state.specialty;
  document.getElementById("sum2-specialty").textContent = state.specialty;
  document.getElementById("sum2-cost").textContent = "S/ " + state.cost;
  filterDoctorsBySpecialty(state.specialty);
  hide("step1");
  hide("step3");
  hide("step4");
  hide("step-success");
  show("step2");
}

function filterDoctorsBySpecialty(specialty) {
  const cards = document.querySelectorAll(".doctor-card");
  let visibleCount = 0;

  cards.forEach((card) => {
    const isMatch = card.dataset.specialty === specialty;
    card.style.display = isMatch ? "" : "none";
    if (isMatch) {
      visibleCount += 1;
    }
  });

  const noDoctors = document.getElementById("noDoctorsMsg");
  if (noDoctors) {
    noDoctors.style.display = visibleCount === 0 ? "block" : "none";
  }
}

// ── STEP 2 ─────────────────────────────────────────────────────────────────
function selectDoctor(el) {
  state.doctor = el.dataset.doctor;
  state.doctorCMP = el.dataset.cmp;
  state.medicoId = el.dataset.idmedico;
  // Avatar index based on doctor name (use card img)
  const img = el.querySelector(".doctor-avatar");
  state.doctorImg = img ? img.src : state.doctorImg;

  document
    .querySelectorAll(".doctor-card")
    .forEach((c) => c.classList.remove("selected"));
  el.classList.add("selected");
  document.getElementById("sum2-doctor").textContent = state.doctor;
  document.getElementById("sum2-doctor").classList.remove("pending");

  // Go to step 3 immediately
  setTimeout(goToStep3, 180);
}

// ── STEP 3 ─────────────────────────────────────────────────────────────────
function goToStep3() {
  state.selectedDate = null;
  state.selectedTime = null;
  document.getElementById("sum3-specialty").textContent = state.specialty;
  document.getElementById("sum3-doctor").textContent = state.doctor;
  document.getElementById("sum3-cmp").textContent = "CMP: " + state.doctorCMP;
  document.getElementById("sum3-docImg").src = state.doctorImg.replace(
    "80",
    "36",
  );
  document.getElementById("sum3-date").textContent = "Selecciona una fecha";
  document.getElementById("sum3-date").classList.add("pending");
  document.getElementById("sum3-time").textContent = "Selecciona una hora";
  document.getElementById("sum3-time").classList.add("pending");
  document.getElementById("step3ContinueBtn").disabled = true;

  renderCalendar();
  renderSlots();
  hide("step1");
  hide("step2");
  hide("step4");
  hide("step-success");
  show("step3");
}

function renderCalendar() {
  const grid = document.getElementById("calGrid");
  const label = document.getElementById("calMonthLabel");
  label.textContent = monthNames[state.calMonth] + " " + state.calYear;

  grid.innerHTML = "";
  // Day labels
  dayNames.forEach((d) => {
    const el = document.createElement("div");
    el.className = "cal-day-label";
    el.textContent = d;
    grid.appendChild(el);
  });

  const firstDay = new Date(state.calYear, state.calMonth, 1).getDay();
  const daysInMonth = new Date(state.calYear, state.calMonth + 1, 0).getDate();

  for (let i = 0; i < firstDay; i++) {
    const el = document.createElement("div");
    el.className = "cal-day empty";
    grid.appendChild(el);
  }

  const today = new Date(); // actual today for demo
  for (let d = 1; d <= daysInMonth; d++) {
    const el = document.createElement("div");
    const isToday = d === 3 && state.calMonth === 9 && state.calYear === 2023; // demo today
    const hasSlot = availDays.includes(d);
    const isSelected = state.selectedDate === d;
    const isPast = d < 3 && state.calMonth === 9 && state.calYear === 2023;

    el.className =
      "cal-day" +
      (isToday ? " today" : "") +
      (hasSlot && !isPast ? " has-slot" : "") +
      (isSelected ? " selected" : "") +
      (isPast ? " disabled" : "");

    el.innerHTML = d + (isToday ? '<span class="today-label">HOY</span>' : "");

    if (!isPast) {
      el.onclick = () => selectDate(d);
    }
    grid.appendChild(el);
  }
}

function selectDate(d) {
  state.selectedDate = d;
  state.selectedTime = null;
  renderCalendar();
  renderSlots();

  const dateStr = d + " de " + monthNames[state.calMonth];
  document.getElementById("sum3-date").textContent = dateStr;
  document.getElementById("sum3-date").classList.remove("pending");
  document.getElementById("sum3-time").textContent = "Selecciona una hora";
  document.getElementById("sum3-time").classList.add("pending");
  checkStep3Ready();
}

function renderSlots() {
  renderSlotGroup("morningSlots", morningTimes);
  renderSlotGroup("afternoonSlots", afternoonTimes);
}

function renderSlotGroup(containerId, times) {
  const container = document.getElementById(containerId);
  container.innerHTML = "";
  times.forEach((t) => {
    const el = document.createElement("div");
    const isOccupied = occupiedSlots.includes(t);
    const isSelected = state.selectedTime === t;
    el.className =
      "time-slot" +
      (isOccupied ? " occupied" : "") +
      (isSelected ? " selected" : "");
    el.textContent = t;
    if (!isOccupied) el.onclick = () => selectTime(t);
    container.appendChild(el);
  });
}

function selectTime(t) {
  state.selectedTime = t;
  renderSlots();
  document.getElementById("sum3-time").textContent = t;
  document.getElementById("sum3-time").classList.remove("pending");
  checkStep3Ready();
}

function checkStep3Ready() {
  const ready = state.selectedDate && state.selectedTime;
  document.getElementById("step3ContinueBtn").disabled = !ready;
}

function prevMonth() {
  if (state.calMonth === 0) {
    state.calMonth = 11;
    state.calYear--;
  } else state.calMonth--;
  renderCalendar();
}
function nextMonth() {
  if (state.calMonth === 11) {
    state.calMonth = 0;
    state.calYear++;
  } else state.calMonth++;
  renderCalendar();
}

// ── STEP 4 ─────────────────────────────────────────────────────────────────
function goToStep4() {
  const dateStr = state.selectedDate + " de " + monthNames[state.calMonth];
  // Determine doctor CMP display
  const cmpNum = state.doctorCMP || "52341";

  document.getElementById("conf-specialty").textContent =
    state.specialty.toUpperCase();
  document.getElementById("conf-doctor").textContent = state.doctor;
  document.getElementById("conf-title").textContent =
    "Médico Especialista – CMP: " + cmpNum;
  document.getElementById("conf-docImg").src = state.doctorImg;
  document.getElementById("conf-cost").textContent = "S/ " + state.cost;
  document.getElementById("conf-date").textContent = dateStr;
  document.getElementById("conf-time").textContent =
    state.selectedTime + " (Puntual)";

  hide("step1");
  hide("step2");
  hide("step3");
  hide("step-success");
  show("step4");
}

function goToStep3() {
  // Defined above — also handles going back from step4
  state.selectedDate = null;
  state.selectedTime = null;
  document.getElementById("sum3-specialty").textContent = state.specialty;
  document.getElementById("sum3-doctor").textContent = state.doctor;
  document.getElementById("sum3-cmp").textContent = "CMP: " + state.doctorCMP;
  document.getElementById("sum3-docImg").src = state.doctorImg.replace(
    "80",
    "36",
  );
  document.getElementById("sum3-date").textContent = "Selecciona una fecha";
  document.getElementById("sum3-date").classList.add("pending");
  document.getElementById("sum3-time").textContent = "Selecciona una hora";
  document.getElementById("sum3-time").classList.add("pending");
  document.getElementById("step3ContinueBtn").disabled = true;
  renderCalendar();
  renderSlots();
  hide("step1");
  hide("step2");
  hide("step4");
  hide("step-success");
  show("step3");
}

// ── SUBMIT ─────────────────────────────────────────────────────────────────
async function submitReservation() {
  const payload = {
    specialty: state.specialty,
    doctor: state.doctor,
    medicoId: state.medicoId,
    doctorCMP: state.doctorCMP,
    date: state.selectedDate + "-" + (state.calMonth + 1) + "-" + state.calYear,
    time: state.selectedTime,
    cost: state.cost,
    sede: "Sede Central – San Isidro",
    patient_id: "PAT-001", // replace with actual session patient
  };

  const spinner = document.getElementById("spinnerOverlay");
  const spinnerMsg = document.getElementById("spinnerMsg");
  const btn = document.getElementById("confirmBtn");

  btn.disabled = true;
  spinnerMsg.textContent = "Procesando tu reserva…";
  spinner.classList.add("show");

  try {
    const res = await fetch("/api/citas/reservar", {
      credentials: "include",
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });

    spinner.classList.remove("show");

    if (res.ok) {
      showSuccess();
    } else {
      const err = await res.json().catch(() => ({}));
      btn.disabled = false;
      alert("Ocurrió un error: " + (err.message || res.statusText));
    }
  } catch (e) {
    spinner.classList.remove("show");
    btn.disabled = false;
    // For demo purposes, show success anyway
    showSuccess();
  }
}

function showSuccess() {
  hide("step1");
  hide("step2");
  hide("step3");
  hide("step4");
  show("step-success");
  window.scrollTo({ top: 0, behavior: "smooth" });
}

function downloadReminder() {
  alert("Recordatorio descargado correctamente.");
}

function resetFlow() {
  state.specialty = "Medicina General";
  state.cost = "80.00";
  state.doctor = "";
  state.selectedDate = null;
  state.selectedTime = null;
  state.calYear = 2023;
  state.calMonth = 9;
  // reset specialty cards
  document
    .querySelectorAll(".specialty-card")
    .forEach((c) => c.classList.remove("selected"));
  document
    .querySelector('[data-specialty="Medicina General"]')
    .classList.add("selected");
  document.getElementById("sum1-specialty").textContent = "Medicina General";
  document.getElementById("sum1-cost").textContent = "S/ 80.00";
  hide("step-success");
  hide("step2");
  hide("step3");
  hide("step4");
  show("step1");
}

function selectDefaultSpecialty() {
  const defaultCard = document.querySelector(
    '.specialty-card[data-specialty="Medicina General"]',
  );
  if (defaultCard) {
    defaultCard.classList.add("selected");
  }
}

// ── INIT ───────────────────────────────────────────────────────────────────
renderCalendar();
renderSlots();
selectDefaultSpecialty();
filterDoctorsBySpecialty(state.specialty);
