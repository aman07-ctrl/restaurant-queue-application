# Admin Queue Flow

## Responsibilities
- Open / close queue
- Set average service time
- View live queue
- Call next customer
- Mark customer as seated
- Handle no-shows

## Queue States
WAITING → CALLED → SERVED
WAITING → CALLED → NO_SHOW

## Key Rules
- Queue order is FIFO per party-size compatibility
- Smaller parties may be seated earlier if suitable tables are available
- Grace period applies after calling a customer
