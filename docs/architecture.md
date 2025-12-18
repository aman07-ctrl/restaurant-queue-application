# System Architecture

## Services
- API Gateway: Entry point
- Queue Service: Queue & ETA logic
- Restaurant Service: Table configuration
- Notification Service: SMS delivery
- Auth Service: OTP authentication

## Communication
- REST for synchronous calls
- Events for async communication

## Database
- PostgreSQL per service
