//
//  SCSDKUserDataQueryBuilder.h
//  SCSDKLoginKit
//
//  Created by Madison Westergaard on 10/28/21.
//  Copyright © 2021 Snap, Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@class SCSDKUserDataQuery;

NS_ASSUME_NONNULL_BEGIN

/// SCSDKUserDataQueryBuilder builds a user data query to fetch user data
@interface SCSDKUserDataQueryBuilder : NSObject

- (instancetype)init;

/**
 For fetching a user's display name
 */
- (instancetype)withDisplayName;

/**
 For fetching a user's external ID
 */
- (instancetype)withExternalId;

/**
 For fetching OpenID Connect ID token
 */
- (instancetype)withIdToken;

/**
 For fetching a user's Bitmoji avatar ID
 */
- (instancetype)withBitmojiAvatarID;

/**
 For fetching a user's 2D Bitmoji Avatar URL
 */
- (instancetype)withBitmojiTwoDAvatarUrl;

/**
 Builds the SCSDKUserDataQuery object 
 */
- (SCSDKUserDataQuery *)build;

@end

NS_ASSUME_NONNULL_END
