"""
Response selection methods determines which response should be used in
the event that multiple responses are generated within a logic adapter.
"""
import logging


def get_most_frequent_response(input_statement, response_list, storage=None):
    """
    :param input_statement: A statement, that closely matches an input to the chat bot.
    :type input_statement: Statement

    :param response_list: A list of statement options to choose a response from.
    :type response_list: list

    :param storage: An instance of a storage adapter to allow the response selection
                    method to access other statements if needed.
    :type storage: StorageAdapter

    :return: The response statement with the greatest number of occurrences.
    :rtype: Statement
    """
    matching_response = None
    occurrence_count = -1

    logger = logging.getLogger(__name__)
    logger.info('Selecting response with greatest number of occurrences.')

    for statement in response_list:
        count = len(list(storage.filter(
            text=statement.text,
            in_response_to=input_statement.text)
        ))

        # Keep the more common statement
        if count >= occurrence_count:
            matching_response = statement
            occurrence_count = count

    # Choose the most commonly occuring matching response
    return matching_response


def get_first_response(input_statement, response_list, storage=None):
    """
    :param input_statement: A statement, that closely matches an input to the chat bot.
    :type input_statement: Statement

    :param response_list: A list of statement options to choose a response from.
    :type response_list: list

    :param storage: An instance of a storage adapter to allow the response selection
                    method to access other statements if needed.
    :type storage: StorageAdapter

    :return: Return the first statement in the response list.
    :rtype: Statement
    """
    logger = logging.getLogger(__name__)
    logger.info('Selecting first response from list of {} options.'.format(
        len(response_list)
    ))
    return response_list[0]


def get_random_response(input_statement, response_list, storage=None):
    """
    :param input_statement: A statement, that closely matches an input to the chat bot.
    :type input_statement: Statement

    :param response_list: A list of statement options to choose a response from.
    :type response_list: list

    :param storage: An instance of a storage adapter to allow the response selection
                    method to access other statements if needed.
    :type storage: StorageAdapter

    :return: Choose a random response from the selection.
    :rtype: Statement
    """
    from random import choice
    logger = logging.getLogger(__name__)
    logger.info('Selecting a response from list of {} options.'.format(
        len(response_list)
    ))
    return choice(response_list)


def get_priority_response(input_statement, response_list, storage=None):
    """
    :param input_statement: A statement, that closely matches an input to the chat bot.
    :type input_statement: Statement

    :param response_list: A list of statement options to choose a response from.
    :type response_list: list

    :param storage: An instance of a storage adapter to allow the response selection
                    method to access other statements if needed.
    :type storage: StorageAdapter

    :return: Choose a random response from the selection.
    :rtype: Statement
    """
    import random
    matching_response = None

    logger = logging.getLogger(__name__)
    logger.info('Selecting response with greatest number of occurrences.')

    # 모든 priority_value의 합을 위해 정의
    total_priority = 0

    # 모든 priority_value의 합을 구함
    for statement in response_list:
        total_priority = total_priority + int(statement.priority_value)

    # 1~priority_value의 합 사이의 random 값을 구함
    random_priority = random.randint(1, total_priority)

    # 구한 random값에서 priority 값을 빼면서 음수가 된다면 response로 return
    for statement in response_list:
        if random_priority - int(statement.priority_value) <= 0:
            matching_response = statement
            break
        elif random_priority - int(statement.priority_value) > 0:
            random_priority = random_priority - int(statement.priority_value)

    # Choose the most commonly occuring matching response
    return matching_response
