// TASK 1
// main.js
// Log welcome message
console.log("Welcome to the Community Portal");
// Alert when the page is fully loaded
window.onload = function() {
    alert("Page is fully loaded. Welcome!");
};
//TASK 2
// Event details
const eventName = "Music Night";
const eventDate = "2025-06-10";
let availableSeats = 100;
// Display event info
console.log(`Event: ${eventName} on ${eventDate}. Seats available: ${availableSeats}`);
// Simulate a registration (decrease seats)
function registerSeat() {
    if (availableSeats > 0) {
        availableSeats--;
        console.log(`Seat registered! Seats left: ${availableSeats}`);
    } else {
        console.log("Sorry, no seats available.");
    }
}
//TASK 3
const events = [
  { name: "Music Night", date: "2025-06-01", seats: 10 },
  { name: "Food Fest", date: "2024-12-15", seats: 0 },   // full event
  { name: "Charity Drive", date: "2025-05-10", seats: 5 },
  { name: "Past Event", date: "2023-01-01", seats: 20 }  // past event
];
function isUpcoming(event) {
  const today = new Date();
  return new Date(event.date) > today;
}
function displayEvents() {
  events.forEach(event => {
    if (isUpcoming(event) && event.seats > 0) {
      console.log(`Upcoming: ${event.name} on ${event.date} with ${event.seats} seats left.`);
    } else {
      console.log(`Skipping event: ${event.name} (either full or past)`);
    }
  });
}
function register(eventName) {
  try {
    const event = events.find(e => e.name === eventName);
    if (!event) throw new Error("Event not found.");
    if (!isUpcoming(event)) throw new Error("Cannot register for past event.");
    if (event.seats <= 0) throw new Error("No seats available.");
    
    event.seats--;  // reduce seat count on registration
    console.log(`Registered for ${event.name}. Seats left: ${event.seats}`);
  } catch (error) {
    console.error(`Registration error: ${error.message}`);
  }
}
// Example usage
displayEvents();
register("Charity Drive");
register("Food Fest");  // Should throw an error for full event
register("Past Event"); // Should throw an error for past event
//TASK 4
events.push({ name: "Art Expo", date: "2025-09-20", seats: 15, category: "Art" });
// Add event function
function addEvent(name, date, seats, category) {
  events.push({ name, date, seats, category });
}

// Closure to track total registrations by category
function registrationTracker() {
  const totals = {};
  return function(category) {
    totals[category] = (totals[category] || 0) + 1;
    return totals[category];
  };
}
const trackRegistration = registrationTracker();
// Register user for an event
function registerUser(eventName) {
  const event = events.find(e => e.name === eventName);
  if (!event) {
    console.error("Event not found.");
    return;
  }
  if (new Date(event.date) <= new Date()) {
    console.error("Cannot register for past event.");
    return;
  }
  if (event.seats <= 0) {
    console.error("No seats available.");
    return;
  }
  event.seats--;
  const totalRegs = trackRegistration(event.category);
  console.log(`Registered for ${eventName}. Seats left: ${event.seats}. Total registrations in ${event.category}: ${totalRegs}`);
}
// Register users
registerUser("Music Night");
registerUser("Music Night");
registerUser("Food Fest");
// Filter events in Music category
const musicEvents = filterEventsByCategory(e => e.category === "Music");
console.log("Music events:", musicEvents);
// Event class definition
//TASK 5
class Event {
  constructor(name, date, seats) {
    this.name = name;
    this.date = new Date(date);
    this.seats = seats;
  }
  // Prototype method to check seat availability
  checkAvailability() {
    return this.seats > 0;
  }
}
// Example usage:
const event1 = new Event("Music Night", "2025-06-01", 10);
const event2 = new Event("Food Fest", "2025-12-15", 0);
// Check availability
console.log(event1.checkAvailability()); // true
console.log(event2.checkAvailability()); // false
// List keys and values of event1
for (const [key, value] of Object.entries(event1)) {
  console.log(`${key}: ${value}`);
}
//TASK 6
// Initial array of community events
let events = [
  { title: "Workshop on Baking", type: "Workshop" },
  { title: "Jazz Night", type: "Music" },
  { title: "Art Exhibition", type: "Exhibition" },
  { title: "Rock Concert", type: "Music" }
];
// Add new events using .push()
events.push({ title: "Folk Music Festival", type: "Music" });
// Use .filter() to show only music events
let musicEvents = events.filter(event => event.type === "Music");
// Use .map() to format display cards
let formattedEvents = events.map(event => `Event: ${event.title}`);
// Output for testing
console.log("All Events:", events);
console.log("Music Events:", musicEvents);
console.log("Formatted Events:", formattedEvents);
//TASK 7
// Sample events array
let events = [
  { title: "Workshop on Baking", registered: false },
  { title: "Jazz Night", registered: false },
  { title: "Art Exhibition", registered: false }
];
// Access container in DOM
const container = document.querySelector("#events-container");
// Function to render events
function renderEvents() {
  container.innerHTML = ""; // Clear previous content

  events.forEach((event, index) => {
    // Create card
    const card = document.createElement("div");
    card.className = "event-card";
    card.textContent = event.title;
    // Create button
    const btn = document.createElement("button");
    btn.textContent = event.registered ? "Cancel" : "Register";
    btn.addEventListener("click", () => {
      events[index].registered = !events[index].registered;
      renderEvents(); // Re-render UI
    });

    card.appendChild(btn);
    container.appendChild(card);
  });
}
// Initial render
renderEvents();
//TASK 8
// Render events
function renderEvents(filtered = events) {
  container.innerHTML = "";
  filtered.forEach((event, index) => {
    const card = document.createElement("div");
    card.textContent = event.title;

    const btn = document.createElement("button");
    btn.textContent = event.registered ? "Cancel" : "Register";
    btn.onclick = () => {
      event.registered = !event.registered;
      renderEvents(filtered);
    };

    card.appendChild(btn);
    container.appendChild(card);
  });
}
// Handle category filter change
filterSelect.onchange = () => {
  const category = filterSelect.value;
  const filtered = category === "All"
    ? events
    : events.filter(e => e.type === category);
  renderEvents(filtered);
};
// Handle keydown for search
searchInput.onkeydown = () => {
  const keyword = searchInput.value.toLowerCase();
  const filtered = events.filter(e =>
    e.title.toLowerCase().includes(keyword)
  );
  renderEvents(filtered);
};
// Initial display
renderEvents();
//TASK  9
// Using Promises with .then() and .catch()
fetch("https://mockapi.io/api/events")
  .then(response => response.json())
  .then(data => {
    console.log("Events fetched with .then():", data);
  })
  .catch(error => {
    console.error("Error fetching events:", error);
  });

// Using async/await with loading spinner
const spinner = document.querySelector("#loading-spinner");
async function fetchEvents() {
  try {
    spinner.style.display = "block"; // Show loading spinner
    const response = await fetch("https://mockapi.io/api/events");
    const data = await response.json();
    spinner.style.display = "none"; // Hide spinner
    renderEvents(data);
  } catch (error) {
    spinner.style.display = "none";
    console.error("Error with async/await fetch:", error);
  }
}
function renderEvents(events) {
  container.innerHTML = "";
  events.forEach(event => {
    const card = document.createElement("div");
    card.textContent = event.title;
    container.appendChild(card);
  });
}
// Call async function
fetchEvents();
//TASK 10
// Function with default parameter and destructuring
const displayEvent = ({ title, type, registered } = {}) => {
  console.log(`Title: ${title}, Type: ${type}, Registered: ${registered}`);
};
// Clone events list using spread operator
const cloneEvents = [...events];
// Filter music events from cloned list
const musicEvents = cloneEvents.filter(({ type }) => type === "Music");
// Display filtered events
musicEvents.forEach(event => displayEvent(event));
// Example of using let and const properly
const addEvent = (eventList, newEvent = { title: "Default Event", type: "General", registered: false }) => {
  let updatedList = [...eventList, newEvent];
  return updatedList;
};
const updatedEvents = addEvent(events, { title: "Rock Concert", type: "Music", registered: false });
//TASK 11
// Access the form
const form = document.querySelector("#registration-form");

// Handle form submission
form.addEventListener("submit", (event) => {
  event.preventDefault(); // Prevent default submission

  const { name, email, eventSelect } = form.elements;

  let hasError = false;

  // Clear previous errors
  document.querySelectorAll(".error").forEach(el => el.textContent = "");

  // Validate name
  if (!name.value.trim()) {
    document.querySelector("#name-error").textContent = "Name is required.";
    hasError = true;
  }

  // Validate email
  if (!email.value.trim() || !email.value.includes("@")) {
    document.querySelector("#email-error").textContent = "Valid email is required.";
    hasError = true;
  }

  // Validate selected event
  if (!eventSelect.value) {
    document.querySelector("#event-error").textContent = "Please select an event.";
    hasError = true;
  }
  // If no errors, log the registration data
  if (!hasError) {
    const formData = {
      name: name.value.trim(),
      email: email.value.trim(),
      selectedEvent: eventSelect.value
    };
    console.log("Form submitted:", formData);
    form.reset();
  }
});
//TASK 12
const form = document.querySelector("#registration-form");
const message = document.querySelector("#message");

form.addEventListener("submit", (e) => {
  e.preventDefault();
  const { name, email, eventSelect } = form.elements;
  const userData = {
    name: name.value,
    email: email.value,
    event: eventSelect.value
  };

  message.textContent = "Submitting...";

  setTimeout(() => {
    fetch("https://mockapi.io/api/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(userData)
    })
    .then(res => res.ok ? "Registration successful!" : "Submission failed.")
    .then(msg => message.textContent = msg)
    .catch(() => message.textContent = "Error sending data.");
  }, 1000);
});
// Task 13: Debugging and Testing
const form = document.querySelector("#registration-form");
form.addEventListener("submit", (e) => {
  e.preventDefault();
  console.log("Form submitted"); // log step
  const { name, email, eventSelect } = form.elements;
  const data = {
    name: name.value,
    email: email.value,
    event: eventSelect.value
  };
  console.log("Payload:", data); // check fetch payload

  fetch("https://mockapi.io/api/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  })
  .then(res => res.json())
  .then(resp => console.log("Server response:", resp))
  .catch(err => console.error("Fetch error:", err));
  // Use Chrome DevTools: set breakpoint here to inspect `data`
});
/*
Task 14: jQuery and JS Frameworks
*/
$("#registerBtn").click(() => {
  $(".event-card").fadeOut(300).fadeIn(300); // jQuery animation
});
// One benefit of React/Vue: Component-based structure makes UI more modular and maintainable.
