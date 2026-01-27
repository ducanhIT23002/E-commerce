# PROJECT CONTEXT – SMART PARKING SYSTEM

## 1. Repository Structure
- Monorepo
- /backend : Spring Boot
- /frontend : ReactJS

---

## 2. Database Overview (ERD)
Main tables:
- users
- vehicles
- parking_lots
- zones
- parking_slots
- price_rates
- bookings
- payments
- staff
- violations

Key relationships:
- users 1–N vehicles
- users 1–N bookings
- vehicles 1–N bookings
- parking_lots 1–N zones
- zones 1–N parking_slots
- parking_slots 1–N bookings
- bookings 1–1 payments
- bookings 1–N violations

---

## 3. Backend Architecture (Spring Boot)
- Layered architecture:
  Controller → Service → Repository
- Business logic MUST be in Service
- Controller only handles request/response
- JPA / Hibernate
- REST API prefix: /api/v1
- Authentication: JWT
- Database: MySQL

Rules:
- Do NOT access repository directly from controller
- Do NOT mix booking logic with payment logic
- Use transaction for booking + payment

---

## 4. Frontend Architecture (React)
- ReactJS
- Ant Design UI
- API calls located in /services
- Components must NOT call API directly
- Pages handle data loading
- JWT stored in localStorage

---

## 5. Core Business Workflow (Customer)

### Phase 1 – Preparation
1. User register / login → users
2. Manage vehicles:
   - Add vehicle (plate_number, type, brand)
   - Saved in vehicles table
3. Top-up wallet:
   - Increase users.balance

---

### Phase 2 – View / Search
1. View parking lots on map/list
2. Select parking lot → zones
3. Select zone → parking_slots
4. Slot status:
   - GREEN = available
   - RED = occupied

---

### Phase 3 – Booking & Payment
1. User selects:
   - parking_slot
   - vehicle (from user vehicles)
   - parking duration (hours)
2. Price calculation:
   - price_rates by zone + vehicle_type
   - total = price_per_hour × hours
3. Backend checks:
   - User balance >= total
   - Slot still available
4. Actions (atomic transaction):
   - Deduct user balance
   - Create booking
   - Update slot status to OCCUPIED
   - Create payment record
5. Result:
   - Booking confirmed
   - Display QR Code + plate number

---

## 6. Status Definitions

### booking.status
- PENDING
- CONFIRMED
- CANCELLED
- COMPLETED

### payment.status
- SUCCESS
- FAILED
- REFUNDED

### parking_slot.status
- AVAILABLE
- OCCUPIED
- MAINTENANCE

---

## 7. Rules for AI Assistant
- Do NOT change database schema unless explicitly asked
- Do NOT rename tables or columns
- Do NOT refactor architecture
- Always follow existing patterns
- Ask before making major changes
