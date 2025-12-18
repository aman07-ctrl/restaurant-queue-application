# Restaurant Real-Time Queue Management System

A production-grade, real-time restaurant queue and ETA calculation system.

## Problem
Restaurants struggle to manage waiting customers, provide accurate wait times,
and handle no-shows efficiently during peak hours.

## Solution
A QR-based queue system where:
- Customers join via OTP after scanning a QR code
- Admins manage the queue in real time
- ETA is calculated based on table size, party size, and availability
- Customers receive SMS notifications when their table is ready

## Tech Stack
- Java 17
- Spring Boot
- Spring Cloud
- PostgreSQL
- WebSocket
- Docker

## Architecture
- API Gateway
- Queue Service
- Restaurant Service
- Notification Service
- Auth Service
