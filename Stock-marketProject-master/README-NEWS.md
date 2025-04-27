# Stock Market News Integration

This branch contains the news integration feature for the Stock Market project.

## Features
- Real-time news fetching from NewsAPI.org
- News categorization and filtering
- Stock-specific news tracking
- Rate limiting for API protection
- CORS configuration
- JWT security

## Setup
1. Get API key from NewsAPI.org
2. Update application.properties with your API key
3. Configure database settings
4. Run the application

## Endpoints
- GET /api/news/recent - Get recent news
- GET /api/news/category/{category} - Get news by category
- GET /api/news/stock/{symbol} - Get news by stock symbol
- GET /api/news/search?query={query} - Search news
- POST /api/news - Create news
- DELETE /api/news/{id} - Delete news
- GET /api/news/{id} - Get news by ID
- PUT /api/news/{id}/sentiment - Update news sentiment

## Security
- Rate limiting: 100 requests per minute
- JWT authentication
- CORS configuration
- API key protection

## Dependencies
- Spring Boot
- MySQL
- NewsAPI.org
- Bucket4j for rate limiting
- JWT for security 